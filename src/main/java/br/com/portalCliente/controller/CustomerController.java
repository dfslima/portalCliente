package br.com.portalCliente.controller;

import br.com.portalCliente.entity.customer.Customer;
import br.com.portalCliente.exception.PortalClienteException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController extends AbstractController {

    @RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "cpfCnpj", required = false) String cpfCnpj,
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "firstResult", required = false, defaultValue = "1") int firstResult,
            @RequestParam(value = "maxResults", required = false, defaultValue = "10") int maxResults) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        List<Customer> result = Customer.search(name, cpfCnpj, type, firstResult, maxResults);

        return new ResponseEntity<String>(Customer.toJsonArray(result, includeParam(), excludeParam()), headers, HttpStatus.OK);
    }


    /**
     * Este método retorna a quantidade de dados de acordo com o parâmetro informado
     *
     * @param name
     * @param cpfCnpj
     * @param type
     * @return jsonArray
     */
    @RequestMapping(value = "/count", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> count(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "cpfCnpj", required = false) String cpfCnpj,
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "startCreateDate", required = false) Date startCreateDate,
            @RequestParam(value = "endCreateDate", required = false) Date endCreateDate) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        Long result = Customer.count(name, cpfCnpj, type, startCreateDate, endCreateDate);
        return new ResponseEntity<String>(toJson("count", result), headers, HttpStatus.OK);
    }

    /**
     * Este método serve como um auto complete para os dados de Cliente
     *
     * @param param
     * @return jsonArray
     */
    @RequestMapping(value = "/autoComplete", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listAutoCompleteJson(
            @RequestParam(value = "name", required = false) String param) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        List<Customer> result = Customer.findAutoComplete(param);

        return new ResponseEntity<>(Customer.toJsonArray(result, includeParam(), excludeParam("updateAt")), headers, HttpStatus.OK);
    }

    /**
     * Este método salva um cliente na base de dados
     *
     * @param json
     * @param uriBuilder
     * @return
     * @throws PortalClienteException
     */
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) throws PortalClienteException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        Customer customer = null;
        try {
            customer = Customer.fromJson(json);
            Customer.save(customer);
        } catch (PortalClienteException c) {
            return new ResponseEntity<String>(toJson("msg", c.getMessage()), headers, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>(toJson("msg", "Ops! Algo não deu certo. Atualize a página e tente novamente"), headers, HttpStatus.BAD_REQUEST);
        }

        RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
        headers.add("Location", uriBuilder.path(a.value()[0] + "/" + customer.getId().toString()).build().toUriString());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    /**
     * Este método traz um objeto de Cliente da base de dados
     *
     * @param id
     * @return json
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {
        Customer customer = Customer.find(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (customer == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(customer.toJson(), headers, HttpStatus.OK);
    }

    /**
     * Este método altera os dados de um Cliente da base de dados
     *
     * @param json
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json, @PathVariable("id") Integer id) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        Customer customer = Customer.fromJson(json);

        try {
            customer.edit(id);
        } catch (Exception e) {
            erro(e.getMessage());
            return new ResponseEntity<String>(messageToJson(), headers, HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

    /**
     * Este método deleta um Cliente da base de dados
     *
     * @param id
     * @return
     * @throws PortalClienteException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) throws PortalClienteException {
        Customer customer = Customer.find(id);
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Type", "application/json; charset=utf-8");

        if (customer == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }

//        if(Property.findByCustomerId(id)) {
//            erro("Existe uma propriedade cadastrada para este cliente. Não é possível deletar.");
//            return new ResponseEntity<String>(messageToJson(), headers, HttpStatus.ACCEPTED);
//        }
//
//        if(Policy.findByCustomerId(id)) {
//            erro("Existe uma proposta cadastrada para este cliente. Não é possível deletar.");
//            return new ResponseEntity<String>(messageToJson(), headers, HttpStatus.ACCEPTED);
//        }

        customer.remove();

        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}