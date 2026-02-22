package com.tauceti.nowdu.auth.handler;

import com.tauceti.nowdu.auth.service.CustomOAuth2UserService;
import com.tauceti.nowdu.global.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final CustomOAuth2UserService oAuth2UserService;

    private static final String REDIRECT_URL = "http://localhost:3000/oauth2/callback";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        String providerId = oidcUser.getSubject();

        Long userId = oAuth2UserService.findUserIdByProviderId("google", providerId);
        String token = jwtProvider.generateToken(userId);

        log.info("OAuth2 로그인 성공 - userId: {}", userId);
        getRedirectStrategy().sendRedirect(request, response, REDIRECT_URL + "?token=" + token);
    }
}