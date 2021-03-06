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

package com.ephesoft.dcma.gwt.admin.bm.client.presenter.functionkey;

import com.ephesoft.dcma.gwt.admin.bm.client.BatchClassManagementController;
import com.ephesoft.dcma.gwt.admin.bm.client.i18n.BatchClassManagementConstants;
import com.ephesoft.dcma.gwt.admin.bm.client.presenter.AbstractBatchClassPresenter;
import com.ephesoft.dcma.gwt.admin.bm.client.view.functionkey.EditFunctionKeyView;
import com.ephesoft.dcma.gwt.core.client.validator.EmptyStringValidator;
import com.ephesoft.dcma.gwt.core.shared.DocumentTypeDTO;
import com.ephesoft.dcma.gwt.core.shared.FunctionKeyDTO;
import com.google.gwt.event.shared.HandlerManager;

/**
 * The presenter for view that shows the edit function key details.
 * 
 * @author Ephesoft
 * @version 1.0
 * @see com.ephesoft.dcma.gwt.admin.bm.client.presenter.AbstractBatchClassPresenter
 */
public class EditFunctionKeyPresenter extends AbstractBatchClassPresenter<EditFunctionKeyView> {

	/**
	 * Constructor.
	 * 
	 * @param controller BatchClassManagementController
	 * @param view EditFunctionKeyView
	 */
	public EditFunctionKeyPresenter(BatchClassManagementController controller, EditFunctionKeyView view) {
		super(controller, view);

	}

	/**
	 * To handle events.
	 * 
	 * @param eventBus HandlerManager
	 */
	@Override
	public void injectEvents(HandlerManager eventBus) {

	}

	/**
	 * Processing to be done on load of this presenter.
	 */
	@Override
	public void bind() {
		if (controller.getSelectedFunctionKeyDTO() != null) {
			view.setMethodName(controller.getSelectedFunctionKeyDTO().getMethodName());
			view.setKeyName(controller.getSelectedFunctionKeyDTO().getShortcutKeyName());
			view.setMethodDescription(controller.getSelectedFunctionKeyDTO().getMethodDescription());
		} else {
			FunctionKeyDTO functionKeyDTO = controller.getMainPresenter().getDocumentTypeViewPresenter().createFunctionKeyDTOObject();
			functionKeyDTO.setMethodName(view.getMethodName());
			functionKeyDTO.setShortcutKeyName(view.getKeyName());
			functionKeyDTO.setMethodDescription(view.getMethodDescription());
			// controller.setAdd(true);
			controller.setSelectedFunctionKeyDTO(functionKeyDTO);
		}
		view.getValidateMethodNameTextBox().addValidator(new EmptyStringValidator(view.getMethodNameTextBox()));
		view.getValidateMethodDescriptionTextBox().addValidator(new EmptyStringValidator(view.getMethodDescriptionTextBox()));
		view.getValidateMethodNameTextBox().toggleValidDateBox();
		view.getValidateMethodDescriptionTextBox().toggleValidDateBox();
		view.getMethodNameTextBox().setFocus(true);
	}

	/**
	 * In case of save click.
	 */
	public void onSave() {
		if (controller.isAdd()) {
			controller.getSelectedDocument().addFunctionKey(controller.getSelectedFunctionKeyDTO());
			controller.setAdd(false);
		}
		controller.getSelectedFunctionKeyDTO().setMethodName(view.getMethodName());
		controller.getSelectedFunctionKeyDTO().setShortcutKeyName(view.getKeyName());
		controller.getSelectedFunctionKeyDTO().setMethodDescription(view.getMethodDescription());

		controller.getMainPresenter().getFunctionKeyViewPresenter().bind();
		controller.getMainPresenter().getFunctionKeyViewPresenter().showFunctionKeyView();
		controller.getMainPresenter().getBatchClassBreadCrumbPresenter().createBreadCrumb(controller.getSelectedFunctionKeyDTO());
	}

	/**
	 * In case of cancel click.
	 */
	public void onCancel() {
		if (controller.isAdd()) {
			controller.getMainPresenter().showDocumentTypeView(controller.getSelectedFunctionKeyDTO().getDocTypeDTO(), true);
			controller.setAdd(false);
		} else {
			controller.getMainPresenter().getFunctionKeyViewPresenter().showFunctionKeyView();
		}

	}

	/**
	 * To clear the fields.
	 */
	public void clearFields() {
		view.setKeyName(BatchClassManagementConstants.EMPTY_STRING);
		view.setMethodName(BatchClassManagementConstants.EMPTY_STRING);
		view.setMethodDescription(BatchClassManagementConstants.EMPTY_STRING);
	}

	/**
	 * To check Key Used Already.
	 * 
	 * @param keyName String
	 * @return boolean
	 */
	public boolean checkKeyUsedAlready(String keyName) {
		boolean result = false;
		DocumentTypeDTO docuTypeDTO = controller.getSelectedDocument();
		if (docuTypeDTO.getFunctionKeyDTOByShorcutKeyName(keyName) != null
				&& docuTypeDTO.getFunctionKeyDTOByShorcutKeyName(keyName) != controller.getSelectedFunctionKeyDTO()) {
			result = true;
		}
		return result;
	}
}
