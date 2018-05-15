package org.sterling.intranet.system.admin.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.sterling.intranet.exceptions.DataExistException;
import org.sterling.intranet.exceptions.ErrorResponse;
import org.sterling.intranet.interfaces.CampaignServices;
import org.sterling.intranet.models.Campaign;

@RestController
@RequestMapping("/system-admin")
public class SystemAdminCampaignController
{
    
    @Autowired
    private CampaignServices campaignServices;
    
    // this will get all campaign information from repository and return in client side in JSON form
    @RequestMapping(value = "/campaigns", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllCampaign()
    {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Campaign> list = campaignServices.getCampaignList();
        if(!list.isEmpty())
        {
            response.put("campaignList", list);
            response.put("hasCampaignList", HttpStatus.OK);
        }
        else if(list.isEmpty())
        {
            response.put("hasCampaignList", HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/campaigns/{campaignId}", method = RequestMethod.GET)
    public ResponseEntity<Campaign> gsetSelectedCampaign(@PathVariable("campaignId") Integer campaignId) throws DataExistException
    {
        Campaign campaign = campaignServices.findCampaignById(campaignId);
        if(campaign == null)
        {
            throw new DataExistException("Campaign doesn\'t exist");
        }
        return new ResponseEntity<Campaign>(campaign, HttpStatus.OK);
    }
    @RequestMapping(value = "/campaign/", params = "campaignName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity validateByCampaigName(@RequestParam(value = "campaignName") String campaignName) throws DataExistException
    {
        
        boolean result = campaignServices.findByCampaigName(campaignName);
        if(result == true)
        {
            throw new DataExistException(campaignName + " already exist");
        }
        
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    
    @RequestMapping(value = "/campaigns/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Campaign> updateCampaign(@RequestBody Campaign campaign)
    {
        campaignServices.updateCampaign(campaign);
        return new ResponseEntity<Campaign>(campaign, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/campaigns", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveCampaign(@RequestBody Campaign campaign, UriComponentsBuilder builder) throws DataExistException
    {
        Campaign result = campaignServices.findByCampaignName(campaign);
        if(result != null)
        {
            //throw new DataExistException("Camapaign name already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        campaign.setDateCreated(LocalDate.now());
        campaignServices.saveCampaign(campaign);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/campaigns/{id}").buildAndExpand(campaign.getCampaignId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
    
    @ExceptionHandler(DataExistException.class)
    public ResponseEntity<ErrorResponse> dataExist(Exception e)
    {
        ErrorResponse errorResponse = new ErrorResponse();
        
        errorResponse.setErrorCode(HttpStatus.CONFLICT.value());
        errorResponse.setErrorMessage(e.getMessage());
        
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.OK);
    }
}











//    @RequestMapping(value = "/campaigns/{campaignName}", method = RequestMethod.GET)
//    public ResponseEntity<Campaign> findByCampaignName(@PathVariable("campaignName") String campaignName) throws DataExistException
//    {
//        
//        Campaign campaign = new Campaign();
//        campaign.setCampaignName(campaignName);
//        
//        Campaign result = campaignServices.findByCampaignName(campaign);
//        
//        if(result != null)
//        {
//            throw new DataExistException("Camapaign name already exist");
//        }
//        else if(result == null)
//        {
//            return ResponseEntity.notFound().build();
//        }
//        
//        return new ResponseEntity<Campaign>(HttpStatus.OK);
//    }