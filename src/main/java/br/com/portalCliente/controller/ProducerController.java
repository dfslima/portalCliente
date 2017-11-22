package br.com.portalCliente.controller;

import br.com.portalCliente.entity.producer.Producer;
import br.com.portalCliente.enumeration.StatusProducer;
import br.com.portalCliente.security.authentication.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/producers")
public class ProducerController extends AbstractController {

    @Autowired
    private AuthService authService;

    @RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json; charset=utf-8")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> listJson(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "cpf", required = false) String cpf,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "status", required = false) StatusProducer status,
            @RequestParam(value = "brokerage", required = false, defaultValue = "0") int brokerage,
            @RequestParam(value = "firstResult", required = false, defaultValue = "1") int firstResult,
            @RequestParam(value = "maxResults", required = false, defaultValue = "10") int maxResults) {

        List<Producer> result = Producer.search(name, cpf, email, status, brokerage, firstResult, maxResults);

        return new ResponseEntity<String>(Producer.toJsonArray(result,
                includeParam("producerBrokerages"), excludeParam()), setHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/count", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> count(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "cpf", required = false) String cpf,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "status", required = false) StatusProducer status,
            @RequestParam(value = "brokerage", required = false, defaultValue = "0") int brokerage) {

        Long result = Producer.count(name, cpf, email, status, brokerage);

        return new ResponseEntity<String>(toJson("count", result), setHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/autoComplete", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> autoComplete(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "brokerageId", required = false) Integer brokerageId) {

        List<Producer> result = Producer.findByAutoComplete(name, brokerageId);

        return new ResponseEntity<String>(Producer.toJsonArray(result,
                includeParam(), excludeParam()), setHeaders(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        Producer.save(json);
        return new ResponseEntity<String>(setHeaders(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> editApp(@RequestBody String json, @PathVariable("id") Integer id) {
        Producer producer = Producer.fromJson(json);
        producer.setId(id);
        producer.setUpdateAt(new Date());
        producer.merge();
        return new ResponseEntity<String>(setHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {

        Producer producer = Producer.find(id);

        if (producer == null) {
            return new ResponseEntity<String>(setHeaders(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(producer.toJson(), setHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {

        Producer producer = Producer.find(id);

        if (producer == null) {
            return new ResponseEntity<String>(setHeaders(), HttpStatus.NOT_FOUND);
        }
        try {
            producer.remove();
        } catch (Exception e) {
            erro(e.getMessage());
            return new ResponseEntity<String>(messageToJson(), setHeaders(), HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<String>(setHeaders(), HttpStatus.OK);
    }
}