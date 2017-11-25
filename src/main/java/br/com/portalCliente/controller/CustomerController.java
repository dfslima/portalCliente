package br.com.portalCliente.controller;

import br.com.portalCliente.entity.customer.Customer;
import br.com.portalCliente.entity.property.Property;
import br.com.portalCliente.entity.proposal.Proposal;
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
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "firstResult", required = false, defaultValue = "1") int firstResult,
            @RequestParam(value = "maxResults", required = false, defaultValue = "10") int maxResults) {

        List<Customer> result = Customer.search(name, cpfCnpj, type, firstResult, maxResults, userId);
        return new ResponseEntity<String>(Customer.toJsonArray(result, includeParam(), excludeParam()), setHeaders(), HttpStatus.OK);
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
            @RequestParam(value = "endCreateDate", required = false) Date endCreateDate,
            @RequestParam(value = "userId", required = false) Integer userId) {

        Long result = Customer.count(name, cpfCnpj, type, startCreateDate, endCreateDate, userId);
        return new ResponseEntity<String>(toJson("count", result), setHeaders(), HttpStatus.OK);
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
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "name", required = false) String param) {

        List<Customer> result = Customer.findAutoComplete(param, userId);
        return new ResponseEntity<>(Customer.toJsonArray(result, includeParam(), excludeParam("updateAt")), setHeaders(), HttpStatus.OK);
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

        Customer customer = null;

        try {
            customer = Customer.fromJson(json);
            Customer.save(customer);
        } catch (PortalClienteException c) {
            return new ResponseEntity<String>(toJson("msg", c.getMessage()), setHeaders(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>(toJson("msg", "Ops! Algo não deu certo. Atualize a página e tente novamente"), setHeaders(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>(setHeaders(), HttpStatus.CREATED);
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

        if (customer == null) {
            return new ResponseEntity<String>(setHeaders(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(customer.toJson(), setHeaders(), HttpStatus.OK);
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

        Customer customer = Customer.fromJson(json);

        try {
            customer.edit(id);
        } catch (Exception e) {
            erro(e.getMessage());
            return new ResponseEntity<String>(messageToJson(), setHeaders(), HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<String>(setHeaders(), HttpStatus.OK);
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

        if (customer == null) {
            return new ResponseEntity<String>(setHeaders(), HttpStatus.NOT_FOUND);
        }

        if (Property.findByCustomerId(id)) {
            erro("Existe uma propriedade cadastrada para este cliente. Não é possível deletar.");
            return new ResponseEntity<String>(messageToJson(), setHeaders(), HttpStatus.ACCEPTED);
        }

        if (Proposal.findByCustomerId(id)) {
            erro("Existe uma proposta cadastrada para este cliente. Não é possível deletar.");
            return new ResponseEntity<String>(messageToJson(), setHeaders(), HttpStatus.ACCEPTED);
        }

        customer.remove();

        return new ResponseEntity<String>(setHeaders(), HttpStatus.OK);
    }
}