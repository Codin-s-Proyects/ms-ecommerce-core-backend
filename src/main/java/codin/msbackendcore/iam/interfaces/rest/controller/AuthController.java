package codin.msbackendcore.iam.interfaces.rest.controller;

import codin.msbackendcore.iam.domain.services.UserCommandService;
import codin.msbackendcore.iam.interfaces.dto.AuthResponse;
import codin.msbackendcore.iam.interfaces.dto.SignInRequest;
import codin.msbackendcore.iam.interfaces.dto.SignUpRequest;
import codin.msbackendcore.shared.domain.exceptions.ServerErrorException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private final UserCommandService userCommandService;

    public AuthController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @Operation(summary = "Registro de usuario", description = "Registra un nuevo usuario en el sistema")
    @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos o usuario ya existente")
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody SignUpRequest req) {
        var signUpCommand = req.toCommand();

        userCommandService.handle(signUpCommand);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Login de usuario", description = "Autentica un usuario con email o username")
    @ApiResponse(responseCode = "200", description = "Login exitoso")
    @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> signIn(@Valid @RequestBody SignInRequest req) {
        var command = req.toCommand();
        var result = userCommandService.handle(command)
                .orElseThrow(() -> new ServerErrorException("error.server_error", new String[]{}));

        var response = new AuthResponse(
                result.userId(),
                result.accessToken(),
                result.refreshToken(),
                result.userType()
        );

        return ResponseEntity.ok(response);
    }
}