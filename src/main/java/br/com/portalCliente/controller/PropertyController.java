package br.com.portalCliente.controller;

import br.com.portalCliente.entity.property.Property;
import br.com.portalCliente.entity.proposal.Proposal;
import br.com.portalCliente.enumeration.PropertyType;
import br.com.portalCliente.exception.PortalClienteException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("properties")
public class PropertyController extends AbstractController {

    @RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> listJson(
            @RequestParam(value = "propertyType", required = false) PropertyType propertyType,
            @RequestParam(value = "status", required = false, defaultValue = "true") boolean status,
            @RequestParam(value = "customerId", required = false) Integer customerId,
            @RequestParam(value = "cpfCnpj", required = false) String cpfCnpj,
            @RequestParam(value = "street", required = false) String street,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "equipmentModel", required = false) String equipmentModel,
            @RequestParam(value = "vehicleLicensePlate", required = false) String vehicleLicensePlate,
            @RequestParam(value = "vehicleModelName", required = false) String vehicleModelName,
            @RequestParam(value = "boatName", required = false) String boatName,
            @RequestParam(value = "vehicleChassis", required = false) String vehicleChassis,
            @RequestParam(value = "vehicleCodeFipe", required = false) String vehicleCodeFipe,
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "firstResult", required = false, defaultValue = "1") int firstResult,
            @RequestParam(value = "maxResults", required = false, defaultValue = "10") int maxResults) {

        List<Property> result = Property.search(propertyType, customerId, cpfCnpj, street, city,
                equipmentModel, vehicleLicensePlate, vehicleModelName, boatName, status, vehicleChassis, vehicleCodeFipe, firstResult, maxResults, userId);

        return new ResponseEntity<>(Property.toJsonArray(result, includeParam("lifeIndividuals")), setHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/count", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> count(
            @RequestParam(value = "propertyType", required = false) PropertyType propertyType,
            @RequestParam(value = "status", required = false, defaultValue = "true") boolean status,
            @RequestParam(value = "customerId", required = false) Integer customerId,
            @RequestParam(value = "cpfCnpj", required = false) String cpfCnpj,
            @RequestParam(value = "street", required = false) String street,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "equipmentModel", required = false) String equipmentModel,
            @RequestParam(value = "vehicleLicensePlate", required = false) String vehicleLicensePlate,
            @RequestParam(value = "vehicleModelName", required = false) String vehicleModelName,
            @RequestParam(value = "boatName", required = false) String boatName,
            @RequestParam(value = "vehicleChassis", required = false) String vehicleChassis,
            @RequestParam(value = "vehicleCodeFipe", required = false) String vehicleCodeFipe,
            @RequestParam(value = "userId", required = false) Integer userId) {

        Long result = Property.count(propertyType, customerId, cpfCnpj, street, city, equipmentModel, vehicleLicensePlate,
                vehicleModelName, boatName, status, vehicleChassis, vehicleCodeFipe, userId);

        return new ResponseEntity<>(toJson("count", result), setHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/autoComplete", headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> listJsonAutoComplete(
            @RequestParam(value = "value", required = false) String value,
            @RequestParam(value = "type", required = false) PropertyType propertyType,
            @RequestParam(value = "customerId", required = false) Integer customerId,
            @RequestParam(value = "userId", required = false) Integer userId) {

        List<Property> result = Property.findAutoComplete(value, propertyType, customerId, userId);

        return new ResponseEntity<>(Property.toJsonArrayAutoComplete(result, includeParam()), setHeaders(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) throws PortalClienteException {

        try {
            Property property = Property.fromJson(json);
            Property p = Property.save(property);

            return new ResponseEntity<>(p.toJson(includeParam()), setHeaders(), HttpStatus.CREATED);

        } catch (PortalClienteException p) {
            return new ResponseEntity<>(toJson("msg", p.getMessage()), setHeaders(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(toJson("msg", "Ops! Algo de errado aconteceu. Atualize a página e tente novamente"), setHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> showJson(@PathVariable("id") Integer id) {

        Property property = Property.find(id);

        if (property == null) {
            return new ResponseEntity<>(setHeaders(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(property.toJson(includeParam()), setHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json, @PathVariable("id") Integer id) throws PortalClienteException {

        try {
            Property property = Property.fromJson(json);
            property.setId(id);
            property.setUpdateAt(new Date());

            if (property.edit() == null) {
                return new ResponseEntity<>(toJson("msg", "Ops! Algo ocorreu de errado. Atualize a página e tente novamente"), setHeaders(), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(setHeaders(), HttpStatus.OK);

        } catch (PortalClienteException p) {
            return new ResponseEntity<>(toJson("msg", p.getMessage()), setHeaders(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(toJson("msg", "Ops! Algo de errado aconteceu. Atualize a página e tente novamente"), setHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) {

        Property property = Property.find(id);

        if (property == null) {
            return new ResponseEntity<>(setHeaders(), HttpStatus.NOT_FOUND);
        }

        if (Proposal.findByPropertyId(id)) {
            erro("Existe uma apólice cadastrada para esta propriedade. Não é possível deletar.");
            return new ResponseEntity<String>(messageToJson(), setHeaders(), HttpStatus.ACCEPTED);
        }

        property.remove();
        return new ResponseEntity<>(setHeaders(), HttpStatus.OK);
    }
}