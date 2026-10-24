package pt.utp.estg.pp.printhub.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Respostas 401 e 403 no formato comum da API: {"error": "mensagem", "code": 401}.
 * Estes erros são decididos pelo filtro do Spring Security, antes de o pedido chegar
 * a um controller, por isso não passam pelo GlobalExceptionHandler.
 */
@Component
public class SecurityErrorHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

    /** 401: pedido sem token, ou com token inválido ou expirado. */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException exception) throws IOException {
        String message = (exception instanceof OAuth2AuthenticationException)
                ? "Sessão inválida ou expirada. Inicie sessão novamente."
                : "É necessário iniciar sessão.";
        response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "Bearer");
        write(response, HttpStatus.UNAUTHORIZED, message);
    }

    /** 403: sessão válida, mas sem permissão (por exemplo, um cliente numa rota /admin). */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException exception) throws IOException {
        write(response, HttpStatus.FORBIDDEN, "Não tem permissão para aceder a este recurso.");
    }

    private static void write(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write("{\"error\":\"" + message + "\",\"code\":" + status.value() + "}");
    }
}