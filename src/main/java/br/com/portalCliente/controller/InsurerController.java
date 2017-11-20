package br.com.portalCliente.controller;

import br.com.portalCliente.entity.insurer.Insurer;
import br.com.portalCliente.exception.PortalClienteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Controller
@RequestMapping("/insurers")
public class InsurerController extends AbstractController {

    @RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "cnpj", required = false) String cnpj,
            @RequestParam(value = "firstResult", required = false, defaultValue = "1") int firstResult,
            @RequestParam(value = "maxResults", required = false, defaultValue = "10") int maxResults) {

        List<Insurer> result = Insurer.search(name, cnpj, firstResult, maxResults);

        return new ResponseEntity<String>(Insurer.toJsonArray(result, includeParam(""), excludeParam("")), setHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/count", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> count(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "cnpj", required = false) String cnpj) {

        Long result = Insurer.count(name, cnpj);

        return new ResponseEntity<String>(toJson("count", result), setHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/autoComplete", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> loadInsurerJson(
            @RequestParam(value = "value", required = false) String value,
            @RequestParam(value = "brokerageId", required = false) Integer brokerageId) {

        List<Insurer> result = Insurer.findByAutoComplete(value, brokerageId);

        return new ResponseEntity<String>(Insurer.toJsonArray(result, includeParam(),
                excludeParam()), setHeaders(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {

        try {
            Insurer.save(json);
            return new ResponseEntity<String>(setHeaders(), HttpStatus.CREATED);

        } catch (PortalClienteException pce) {
            erro(pce.getMessage());
            return new ResponseEntity<String>(messageToJson(), setHeaders(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            erro(MESSAGE_ERROR_GENERAL);
            return new ResponseEntity<String>(messageToJson(), setHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {

        Insurer insurance = Insurer.find(id);
        if (insurance == null) {
            return new ResponseEntity<String>(setHeaders(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(insurance.toJson(), setHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json, @PathVariable("id") Integer id) {

        try {
            Insurer.edit(json, id);
            return new ResponseEntity<String>(setHeaders(), HttpStatus.CREATED);

        } catch (PortalClienteException pce) {
            erro(pce.getMessage());
            return new ResponseEntity<String>(messageToJson(), setHeaders(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            erro(MESSAGE_ERROR_GENERAL);
            return new ResponseEntity<String>(messageToJson(), setHeaders(), HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {

        try {
            Insurer.delete(id);
            return new ResponseEntity<String>(setHeaders(), HttpStatus.CREATED);

        } catch (PortalClienteException pce) {
            erro(pce.getMessage());
            return new ResponseEntity<String>(messageToJson(), setHeaders(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            erro(MESSAGE_ERROR_GENERAL);
            return new ResponseEntity<String>(messageToJson(), setHeaders(), HttpStatus.BAD_REQUEST);
        }
    }
}