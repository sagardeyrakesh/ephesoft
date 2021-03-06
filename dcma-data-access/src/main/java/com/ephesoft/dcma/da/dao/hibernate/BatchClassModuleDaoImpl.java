/********************************************************************************* 
* Ephesoft is a Intelligent Document Capture and Mailroom Automation program 
* developed by Ephesoft, Inc. Copyright (C) 2010-2012 Ephesoft Inc. 
* 
* This program is free software; you can redistribute it and/or modify it under 
* the terms of the GNU Affero General Public License version 3 as published by the 
* Free Software Foundation with the addition of the following permission added 
* to Section 15 as permitted in Section 7(a): FOR ANY PART OF THE COVERED WORK 
* IN WHICH THE COPYRIGHT IS OWNED BY EPHESOFT, EPHESOFT DISCLAIMS THE WARRANTY 
* OF NON INFRINGEMENT OF THIRD PARTY RIGHTS. 
* 
* This program is distributed in the hope that it will be useful, but WITHOUT 
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
* FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more 
* details. 
* 
* You should have received a copy of the GNU Affero General Public License along with 
* this program; if not, see http://www.gnu.org/licenses or write to the Free 
* Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 
* 02110-1301 USA. 
* 
* You can contact Ephesoft, Inc. headquarters at 111 Academy Way, 
* Irvine, CA 92617, USA. or at email address info@ephesoft.com. 
* 
* The interactive user interfaces in modified source and object code versions 
* of this program must display Appropriate Legal Notices, as required under 
* Section 5 of the GNU Affero General Public License version 3. 
* 
* In accordance with Section 7(b) of the GNU Affero General Public License version 3, 
* these Appropriate Legal Notices must retain the display of the "Ephesoft" logo. 
* If the display of the logo is not reasonably feasible for 
* technical reasons, the Appropriate Legal Notices must display the words 
* "Powered by Ephesoft". 
********************************************************************************/ 

package com.ephesoft.dcma.da.dao.hibernate;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ephesoft.dcma.core.dao.hibernate.HibernateDao;
import com.ephesoft.dcma.da.dao.BatchClassModuleDao;
import com.ephesoft.dcma.da.domain.BatchClassModule;
import com.ephesoft.dcma.da.domain.Module;

/**
 * This is dao class for batch class module.
 * 
 * @author Ephesoft
 * @version 1.0
 * @see com.ephesoft.dcma.da.dao.BatchClassModuleDao
 */
@Repository
public class BatchClassModuleDaoImpl extends HibernateDao<BatchClassModule> implements BatchClassModuleDao {

	/**
	 * BATCH_CLASS String.
	 */
	private static final String BATCH_CLASS = "batchClass";
	
	/**
	 * MODULE String.
	 */
	private static final String MODULE = "module";
	
	/**
	 * BATCH_CLASS_IDENTIFIER String.
	 */
	private static final String BATCH_CLASS_IDENTIFIER = "batchClass.identifier";
	
	/**
	 * LOG to print the logging information.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(BatchClassModuleDaoImpl.class);
	
	/**
	 * WORKFLOW_NAME String.
	 */
	private static final String WORKFLOW_NAME = "workflowName";

	/**
	 * API to get the count of modules related to a batch class.
	 * 
	 * @param batchClassId
	 * @return Integer (count of modules)
	 */
	@Override
	public Integer countModules(String batchClassIdentifier) {
		LOG.info("batchClassIdentifier : " + batchClassIdentifier);
		DetachedCriteria criteria = criteria();
		criteria.createAlias(BATCH_CLASS, BATCH_CLASS);
		criteria.add(Restrictions.eq(BATCH_CLASS_IDENTIFIER, batchClassIdentifier));
		return count(criteria);
	}

	/**
	 * API to fetch List of modules corresponding to a batch class.
	 * 
	 * @param batchClassId
	 * @return List<Module>
	 */
	@Override
	public List<Module> getBatchClassModule(String batchClassIdentifier) {
		DetachedCriteria criteria = criteria();
		criteria.createAlias(BATCH_CLASS, BATCH_CLASS);
		criteria.add(Restrictions.eq(BATCH_CLASS_IDENTIFIER, batchClassIdentifier));
		criteria.setProjection(Projections.property(MODULE));
		return find(criteria);
	}

	/**
	 * API to fetch Modules starting from firstIndex and as many results as MaxResults from a batch class.
	 * 
	 * @param batchClassId
	 * @param firstIndex
	 * @param maxResults
	 * @return List<Module>
	 */
	@Override
	public List<Module> getModules(String batchClassIdentifier, int firstIndex, int maxResults) {
		DetachedCriteria criteria = criteria();
		criteria.createAlias(BATCH_CLASS, BATCH_CLASS);
		criteria.add(Restrictions.eq(BATCH_CLASS_IDENTIFIER, batchClassIdentifier));
		criteria.setProjection(Projections.property(MODULE));
		criteria.addOrder(Order.asc("orderNumber"));
		return find(criteria, firstIndex, maxResults);
	}

	/**
	 * API to fetch the modules by name.
	 * 
	 * @param batchClassIdentifier
	 * @param moduleName
	 * @return BatchClassModule
	 */ 
	@Override
	public BatchClassModule getModuleByName(String batchClassIdentifier, String moduleName) {
		DetachedCriteria criteria = criteria();
		criteria.createAlias(BATCH_CLASS, BATCH_CLASS);
		criteria.createAlias(MODULE, MODULE);
		criteria.add(Restrictions.eq(BATCH_CLASS_IDENTIFIER, batchClassIdentifier));
		criteria.add(Restrictions.eq("module.name", moduleName));
		return findSingle(criteria);
	}

	/**
	 * API to fetch the modules by workflow name.
	 * 
	 * @param batchClassIdentifier
	 * @param workflowName
	 * @return batch class module object
	 */
	@Override
	public BatchClassModule getModuleByWorkflowName(String batchClassIdentifier, String workflowName) {
		DetachedCriteria criteria = criteria();
		criteria.createAlias(BATCH_CLASS, BATCH_CLASS);
		criteria.add(Restrictions.eq(BATCH_CLASS_IDENTIFIER, batchClassIdentifier));
		criteria.add(Restrictions.eq(WORKFLOW_NAME, workflowName));
		return findSingle(criteria);
	}

	/**
	 * API to fetch all batch class modules in order.
	 * @param order Order
	 * @return List<BatchClassModule>
	 */
	@Override
	public List<BatchClassModule> getAllBatchClassModulesInOrder(com.ephesoft.dcma.core.common.Order order) {

		DetachedCriteria criteria = criteria();
		if (order != null) {
			if (order.isAscending()) {
				criteria.addOrder(Order.asc(order.getSortProperty().getProperty()));
			} else {
				criteria.addOrder(Order.desc(order.getSortProperty().getProperty()));
			}
		}
		return find(criteria);
	}

}
