package br.com.portalCliente.controller;

import br.com.portalCliente.entity.user.User;
import br.com.portalCliente.enumeration.Profile;
import br.com.portalCliente.exception.PortalClienteException;
import br.com.portalCliente.security.Crypt;
import br.com.portalCliente.security.authentication.AuthService;
import br.com.portalCliente.security.authentication.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController extends AbstractController {

    @Autowired
    private AuthService authentication;

    @RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> listJson(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "login", required = false) String login,
            @RequestParam(value = "profile", required = false) Profile profile,
            @RequestParam(value = "brokerage", required = false, defaultValue = "0") int brokerage,
            @RequestParam(value = "firstResult", required = false, defaultValue = "1") int firstResult,
            @RequestParam(value = "maxResults", required = false, defaultValue = "10") int maxResults) {

        List<User> result = User.search(name, login, profile, brokerage, firstResult, maxResults);
        return new ResponseEntity<String>(User.toJsonArray(result,
                includeParam("userBrokerages"), excludeParam()), setHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/count", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> count(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "login", required = false) String login,
            @RequestParam(value = "profile", required = false) Profile profile) {

        Long result = User.count(name, login, profile, 0);
        return new ResponseEntity<String>(toJson("count", result), setHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/autoComplete", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> autoComplete(
            @RequestParam(value = "value", required = false) String value,
            @RequestParam(value = "profile", required = false) Profile profile,
            @RequestParam(value = "firstResult", required = false, defaultValue = "1") int firstResult,
            @RequestParam(value = "maxResults", required = false, defaultValue = "10") int maxResults,
            @RequestParam(value = "brokerage", required = false) Integer... brokerage) {

        List<User> result = User.autoComplete(value, profile, firstResult, maxResults, brokerage);

        return new ResponseEntity<String>(User.toJsonArray(result), setHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Integer id,
                                           @RequestParam(value = "brokerageId", required = false) Integer brokerageId) {

        User user = User.find(id);

        if (user == null) {
            return new ResponseEntity<String>(setHeaders(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(user.toJson(includeParam(), excludeParam()), setHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJsonLogin(
            @RequestParam(value = "login", required = false) String login,
            @RequestParam(value = "brokerageId", required = false) Integer brokerageId) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        User user = User.findByLogin(login);

        if (user == null) {
            erro("Usuário ou senha incorretos");
            return new ResponseEntity<String>(messageToJson(), headers, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>(user.toJson(includeParam(), excludeParam("authority")), headers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(
            @RequestParam(value = "brokerageId", required = false) Integer brokerageId,
            @RequestBody String json, UriComponentsBuilder uriBuilder) {

        try {
            User.save(json);
            return new ResponseEntity<String>(setHeaders(), HttpStatus.CREATED);

        } catch (PortalClienteException ue) {
            erro(ue.getMessage());
            return new ResponseEntity<String>(messageToJson(), setHeaders(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            erro("Ops! Ocorreu um problema. Atualize a página e tente novamante");
            return new ResponseEntity<String>(messageToJson(), setHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json, @PathVariable("id") Integer id) {

        User user = User.fromJson(json);
        user.setId(id);

        if (user.edit() == null) {
            return new ResponseEntity<String>(setHeaders(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(setHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/changepassword/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> changePassword(
            @PathVariable("id") Integer id,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "newPassword", required = true) String newPassword) {

        User user = User.find(id);

        newPassword = Crypt.encrypt(newPassword);
        password = Crypt.encrypt(password);

        if (!password.equals(user.getPassword())) {
            erro("A senha atual digitada é inválida");
            return new ResponseEntity<String>(messageToJson(), setHeaders(), HttpStatus.ACCEPTED);
        }

        if (password.equals(newPassword)) {
            erro("A nova senha é igual à senha atual. Favor, digite uma senha diferente");
            return new ResponseEntity<String>(messageToJson(), setHeaders(), HttpStatus.ACCEPTED);
        }

        user.setPassword(newPassword);

        if (user.merge() == null) {
            return new ResponseEntity<String>(setHeaders(), HttpStatus.NOT_FOUND);
        }

        UserAuth userAuth = new UserAuth(user);

        return new ResponseEntity<String>(toJson("userAuth", userAuth), setHeaders(), HttpStatus.OK);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {


        User.deleteUser(id);
        return new ResponseEntity<String>(setHeaders(), HttpStatus.OK);
    }
}
