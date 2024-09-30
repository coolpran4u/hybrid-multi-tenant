package self.pg.hybrid_multi_tenant.central.controller.partner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import self.pg.hybrid_multi_tenant.central.service.partner.PartnerService;
import self.pg.hybrid_multi_tenant.central.vo.partner.PartnerVO;

import java.util.List;

@RestController
@RequestMapping("/tenants")
public class PartnerController {

    @Autowired
    private PartnerService partnerService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PartnerVO> registerPartner(@RequestBody PartnerVO partner) {
       var savedPartnerDetails = partnerService.registerPartner(partner);
       return new ResponseEntity<>(savedPartnerDetails, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<PartnerVO>> getPartners() {
        var savedPartnerDetails = partnerService.getPartners();
        return new ResponseEntity<>(savedPartnerDetails, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartnerVO> getPartner(@PathVariable int id) {
        var savedPartnerDetails = partnerService.getPartner(id);
        return new ResponseEntity<>(savedPartnerDetails, HttpStatus.OK);
    }

}
