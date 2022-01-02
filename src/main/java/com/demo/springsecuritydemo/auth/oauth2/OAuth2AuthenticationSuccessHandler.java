package com.demo.springsecuritydemo.auth.oauth2;

import com.demo.springsecuritydemo.auth.config.property.AppProperties;
import com.demo.springsecuritydemo.auth.constant.Role;
import com.demo.springsecuritydemo.auth.exception.BadRequestException;
import com.demo.springsecuritydemo.auth.model.CustomAuthority;
import com.demo.springsecuritydemo.auth.util.CookieUtils;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AppProperties appProperties;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private static final String REDIRECT_URI_PARAM_COOKIE_NAME = HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;
    private static final String TOKEN_PARAMETER_NAME = "token";
    private static final String AUTHORITIES_PARAMETER_NAME = "authorities";
    private static final String AUTHORITY_DELIMITER = ",";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request,
            REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue);

        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new BadRequestException(
                "Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        String sessionId = request.getSession().getId();
        String authorities = getAuthorities((Collection<CustomAuthority>) authentication.getAuthorities());

        return UriComponentsBuilder.fromUriString(targetUrl)
            .queryParam(TOKEN_PARAMETER_NAME, sessionId)
            .queryParam(AUTHORITIES_PARAMETER_NAME, authorities)
            .build().toUriString();
    }

    private String getAuthorities(Collection<CustomAuthority> authorities) {
         return authorities.stream()
            .map(CustomAuthority::getRole)
            .map(Role::name)
            .collect(Collectors.joining(AUTHORITY_DELIMITER));
    }

    private void clearAuthenticationAttributes(HttpServletRequest request,
        HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request,
            response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);
        return appProperties.getOauth2().getAuthorizedRedirectUris()
            .stream()
            .anyMatch(each -> validateHostAndPort(each, clientRedirectUri));
    }

    private boolean validateHostAndPort(String authorizedRedirectUri, URI clientRedirectUri) {
        URI authorizedURI = URI.create(authorizedRedirectUri);
        return authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
            && authorizedURI.getPort() == clientRedirectUri.getPort();
    }
}
