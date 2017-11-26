package br.com.portalCliente.controller;

import br.com.portalCliente.entity.proposal.Proposal;
import br.com.portalCliente.enumeration.ProposalStatus;
import br.com.portalCliente.exception.PortalClienteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/proposals")
public class ProposalController extends AbstractController {

    /**
     * Este método lista as apólices, de acordo com o parâmetro informado
     *
     * @throws PortalClienteException
     */
    @RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> listJson(
            @RequestParam(value = "proposal", required = false) String proposal,
            @RequestParam(value = "board", required = false) String board,
            @RequestParam(value = "customerId", required = false) Integer customerId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "cpfCnpj", required = false) String cpfCnpj,
            @RequestParam(value = "startTransmissionDate", required = false) Date startTransmissionDate,
            @RequestParam(value = "endTransmissionDate", required = false) Date endTransmissionDate,
            @RequestParam(value = "insurerId", required = false) Integer insurerId,
            @RequestParam(value = "producerId", required = false) Integer producerId,
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "firstResult", required = false, defaultValue = "1") int firstResult,
            @RequestParam(value = "maxResults", required = false, defaultValue = "10") int maxResults) throws PortalClienteException {

        List<Proposal> result = Proposal.search(proposal, board, customerId, name, cpfCnpj, startTransmissionDate,
                endTransmissionDate, insurerId, producerId, firstResult, maxResults, userId);

        return new ResponseEntity<String>(Proposal.toJsonArray(result, includeParam(), excludeParam()), setHeaders(), HttpStatus.OK);
    }

    /**
     * Este método realiza uma contagem de regitros para prover a paginação na tabela
     *
     * @param proposal
     * @param board
     * @param customerId
     * @param name
     * @param cpfCnpj
     * @param startTransmissionDate
     * @param endTransmissionDate
     * @param insurerId
     * @param producerId
     * @return
     * @throws PortalClienteException
     */
    @RequestMapping(value = "/count", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> count(
            @RequestParam(value = "proposal", required = false) String proposal,
            @RequestParam(value = "board", required = false) String board,
            @RequestParam(value = "customerId", required = false) Integer customerId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "cpfCnpj", required = false) String cpfCnpj,
            @RequestParam(value = "startTransmissionDate", required = false) Date startTransmissionDate,
            @RequestParam(value = "endTransmissionDate", required = false) Date endTransmissionDate,
            @RequestParam(value = "insurerId", required = false) Integer insurerId,
            @RequestParam(value = "producerId", required = false) Integer producerId,
            @RequestParam(value = "userId", required = false) Integer userId) throws PortalClienteException {

        Long result = Proposal.count(proposal, board, customerId, name, cpfCnpj, startTransmissionDate, endTransmissionDate,
                insurerId, producerId, userId);

        return new ResponseEntity<String>(toJson("count", result), setHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> showJson(@PathVariable("id") Integer id) throws PortalClienteException {
        return new ResponseEntity<String>(Proposal.find(id).toJson(includeParam(), excludeParam()), setHeaders(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) throws PortalClienteException {

        try {
            Proposal proposal = Proposal.fromJson(json);
            proposal.save(proposal);
            return new ResponseEntity<String>(proposal.toJson(), setHeaders(), HttpStatus.CREATED);
        } catch (PortalClienteException e) {
            erro(e.getMessage());
            return new ResponseEntity<String>(messageToJson(), setHeaders(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
            erro("Ops! Aconteceu algum problema. Atualize a página e tente novamente");
            return new ResponseEntity<String>(messageToJson(), setHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json, @PathVariable("id") Integer id) throws PortalClienteException {

        Proposal proposal = Proposal.fromJson(json);
        proposal.setId(id);

        try {
            if (Proposal.edit(proposal) == null) {
                erro("Erro ao tentar alterar a apólice");
                return new ResponseEntity<String>(messageToJson(), setHeaders(), HttpStatus.BAD_REQUEST);
            }
        } catch (PortalClienteException e) {
            erro(e.getMessage());
            return new ResponseEntity<String>(messageToJson(), setHeaders(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>(setHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    @Transactional
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id) throws PortalClienteException {

        try {
            // Instanciado, pois o delete() chama um método @Autowired
            Proposal proposal = new Proposal();
            proposal.delete(id);

        } catch (PortalClienteException e) {
            erro(e.getMessage());
            return new ResponseEntity<String>(messageToJson(), setHeaders(), HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<String>(setHeaders(), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/validate", headers = "Accept=application/json")
    public ResponseEntity<String> validatePolicy(
            @RequestParam(value = "propertyId", required = false) int propertyId) throws PortalClienteException {
        return new ResponseEntity<String>(toJson("validate", Proposal.existsProposalForProperty(propertyId)), setHeaders(), HttpStatus.OK);
    }
}