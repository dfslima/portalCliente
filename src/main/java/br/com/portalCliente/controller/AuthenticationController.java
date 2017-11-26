package br.com.portalCliente.controller;

import br.com.portalCliente.entity.user.User;
import br.com.portalCliente.exception.PortalClienteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.portalCliente.security.authentication.AuthService;
import br.com.portalCliente.security.authentication.UserAuth;

@Controller
@RequestMapping("/auth")
public class AuthenticationController extends AbstractController {

    @Autowired
    private AuthService authService;

    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> login(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        UserAuth userAuth = UserAuth.fromJson(json);
        try {
            User user = authService.authentication(userAuth, false);
            return new ResponseEntity<String>(user.toJson(), headers, HttpStatus.OK);
        } catch (BadCredentialsException bc) {
            erro("Usuário ou senha incorreto");
            return new ResponseEntity<String>(messageToJson(), headers, HttpStatus.ACCEPTED);

        } catch (AuthenticationException e) {
            erro("E-mail ou senha incorreto");
            return new ResponseEntity<String>(messageToJson(), headers, HttpStatus.ACCEPTED);
        } catch (PortalClienteException pce) {
            erro(pce.getMessage());
            return new ResponseEntity<String>(messageToJson(), headers, HttpStatus.ACCEPTED);
        }
    }
}