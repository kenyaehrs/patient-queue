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


package org.openmrs.module.patientqueue.schedule;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.PatientQueueService;
import org.openmrs.module.hospitalcore.model.OpdPatientQueue;
import org.openmrs.module.hospitalcore.model.OpdPatientQueueLog;
import org.openmrs.scheduler.tasks.AbstractTask;

/**
 * <p> Class: ExampleTask </p>
 * <p> Package: org.openmrs.module.inventory.schedule </p> 
 * <p> Author: Nguyen manh chuyen </p>
 * <p> Update by: Nguyen manh chuyen </p>
 * <p> Version: $1.0 </p>
 * <p> Create date: Feb 24, 2011 4:31:57 PM </p>
 * <p> Update date: Feb 24, 2011 4:31:57 PM </p>
 **/
public class LogOpdPatientQeueTask  extends AbstractTask {
	Log log = LogFactory.getLog(getClass());
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		log.info("Excute LogOpdPatientQeueTask");
		try {
			Context.openSession();
			/*if (!Context.isAuthenticated()) 
			{
				authenticate();
			}*/
			// do something
			
			PatientQueueService queueService = Context.getService(PatientQueueService.class);
			List<OpdPatientQueue> items = queueService.getAllPatientInQueue();
			if( items != null && items.size() > 0 )
			{
				for( OpdPatientQueue item : items ){
					OpdPatientQueueLog log = queueService.copyTo(item);
					queueService.saveOpdPatientQueueLog(log);
					queueService.deleteOpdPatientQueue(item);
				}
			}
			
			Context.closeSession();
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e);
		}
		
	}
	
	@Override
	protected void authenticate() {
		// TODO Auto-generated method stub
		super.authenticate();
	}

	@Override
	public void shutdown() {
		log.info("Shutdown LogOpdPatientQeueTask");
		super.shutdown();
	}

}
