/**
 *  Copyright 2010 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Patient-queue module.
 *
 *  Patient-queue module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Patient-queue module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Patient-queue module.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/

package org.openmrs.module.patientqueue.web.controller.ajax;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.PatientQueueService;
import org.openmrs.module.hospitalcore.model.OpdPatientQueue;
import org.openmrs.module.hospitalcore.model.TriagePatientQueue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * Class: OpdPatientQueueAjaxController
 * </p>
 * <p>
 * Package: org.openmrs.module.patientqueue.web.controller.ajax
 * </p>
 * <p>
 * Author: Nguyen manh chuyen
 * </p>
 * <p>
 * Update by: Nguyen manh chuyen
 * </p>
 * <p>
 * Version: $1.0
 * </p>
 * <p>
 * Create date: Feb 17, 2011 5:20:08 PM
 * </p>
 * <p>
 * Update date: Feb 17, 2011 5:20:08 PM
 * </p>
 **/
@Controller("OpdPatientQueueAjaxController")
public class OpdPatientQueueAjaxController {
	@RequestMapping(value = "/module/patientqueue/opdPatientQueueAjax.htm", method = RequestMethod.GET)
	public String viewOpdPatientQueue(@RequestParam("opdId") Integer opdId,
			Map<String, Object> model, Model modl, HttpServletRequest request) {
		if (opdId != null && opdId > 0) {
			ConceptService conceptService = Context.getConceptService();
			Concept con = conceptService.getConcept(opdId);
			PatientQueueService patientQueueService = Context
					.getService(PatientQueueService.class);
			ConceptAnswer conans = patientQueueService.getConceptAnswer(con);
			String str = conans.getConcept().getName().toString();
			if (str.equals("TRIAGE")) {
				List<TriagePatientQueue> patientQueues = patientQueueService
						.listTriagePatientQueue(null, opdId, "", 0, 0);
				model.put("patientQueues", patientQueues);
				modl.addAttribute("user","triageUser");
			} else if (str.equals("OPD WARD") || str.equals("SPECIAL CLINIC")) {
				List<OpdPatientQueue> patientQueues = patientQueueService
						.listOpdPatientQueue(null, opdId, "", 0, 0);
				model.put("patientQueues", patientQueues);
				modl.addAttribute("user","opdUser");
			}
		}
		return "/module/patientqueue/ajax/opdPatientQueueAjax";
	}
}
