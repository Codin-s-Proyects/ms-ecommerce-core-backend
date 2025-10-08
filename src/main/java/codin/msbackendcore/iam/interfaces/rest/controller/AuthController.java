package codin.msbackendcore.iam.interfaces.rest.controller;

import codin.msbackendcore.iam.application.internal.dto.AuthResponse;
import codin.msbackendcore.iam.application.internal.dto.SignInRequest;
import codin.msbackendcore.iam.application.internal.dto.SignUpRequest;
import codin.msbackendcore.iam.application.internal.outboundservices.token.TokenService;
import codin.msbackendcore.iam.domain.model.commands.SignInCommand;
import codin.msbackendcore.iam.domain.services.UserCommandService;
import codin.msbackendcore.shared.domain.exceptions.AuthenticatedException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.apache.commons.lang3.tuple.ImmutablePair;
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
    private final TokenService tokenService;

    public AuthController(UserCommandService userCommandService, TokenService tokenService) {
        this.userCommandService = userCommandService;
        this.tokenService = tokenService;
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
        var tokens = generateTokens(req.identifier());

        var signInCommand = new SignInCommand(
                req.tenantId(),
                req.identifier(),
                req.password()
        );

        var user = userCommandService.handle(signInCommand)
                .orElseThrow(() -> new AuthenticatedException("error.authorization", new String[]{}, "username,password"));

        var response = new AuthResponse(user.getId(), tokens.left, tokens.right, user.getUserType());

        return ResponseEntity.ok(response);
    }

    private ImmutablePair<String, String> generateTokens(String identifier) {
        var accessToken = tokenService.generateToken(identifier);
        var refreshToken = tokenService.generateRefreshToken(identifier);

        return ImmutablePair.of(accessToken, refreshToken);
    }
}