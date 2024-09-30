package self.pg.hybrid_multi_tenant.tenant.controller.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import self.pg.hybrid_multi_tenant.central.vo.partner.PartnerVO;
import self.pg.hybrid_multi_tenant.tenant.service.school.SchoolService;
import self.pg.hybrid_multi_tenant.tenant.vo.school.SchoolVO;

import java.util.List;

@RestController
@RequestMapping("/schools")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SchoolVO> registerPartner(@RequestBody SchoolVO school) {
        var savedSchoolDetails = schoolService.registerSchool(school);
        return new ResponseEntity<>(savedSchoolDetails, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SchoolVO>> getPartners() {
        var schoolDetails = schoolService.getSchools();
        return new ResponseEntity<>(schoolDetails, HttpStatus.OK);
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SchoolVO> getPartner(@PathVariable int id) {
        var schoolDetail = schoolService.getSchool(id);
        return new ResponseEntity<>(schoolDetail, HttpStatus.OK);
    }
}
