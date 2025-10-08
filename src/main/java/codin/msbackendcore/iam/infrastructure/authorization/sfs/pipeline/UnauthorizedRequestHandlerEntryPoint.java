package codin.msbackendcore.iam.infrastructure.authorization.sfs.pipeline;

import codin.msbackendcore.shared.domain.model.exceptions.AuthenticationError;
import codin.msbackendcore.shared.domain.model.exceptions.ErrorResponse;
import codin.msbackendcore.shared.infrastructure.utils.MessageUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.Locale;

@Component
public class UnauthorizedRequestHandlerEntryPoint implements AuthenticationEntryPoint {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(UnauthorizedRequestHandlerEntryPoint.class);

    private final MessageUtils messageUtils;

    public UnauthorizedRequestHandlerEntryPoint(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authenticationException) throws IOException, ServletException {


        LOGGER.error("Unauthorized request: {}", authenticationException.getMessage());

        Locale locale = request.getLocale();

        String transformedMessage = messageUtils.getMessage("error.unauthorized", locale);

        AuthenticationError error = new AuthenticationError(
                HttpStatus.UNAUTHORIZED.value(),
                transformedMessage,
                "authentication"
        );

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(new ErrorResponse(Collections.singletonList(error))));
    }
}
