package quickstart.sample;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cloud.artik.api.DevicesManagementApi;
import cloud.artik.client.ApiCallback;
import cloud.artik.client.ApiClient;
import cloud.artik.client.ApiException;
import cloud.artik.model.TaskEnvelope;
import cloud.artik.model.TaskParameters;
import cloud.artik.model.TaskRequest;

public class QuickStartTasks {
	
	/**
	 * A connected LWM2M device will receive the tasks that are issued below.
	 * 
	 * Visit the ARTIK Cloud device management dashboard to view your task statuses:
	 * https://developer.artik.cloud/dashboard/devicetypes/your_device_type_id/devices
	 * 
	 */
	
	public static void main (String args[]) {
	
		/** user access token will be require to run this sample */
		ApiClient apiClient = null;
		ClientHelper.initClient(apiClient, Config.USER_TOKEN);
		
		/** initialize device management api service **/
		DevicesManagementApi devicesManagementApi = new DevicesManagementApi();
		
		/** Response handler for calling our async api calls */
		ApiCallback<TaskEnvelope> taskAsyncCallback = taskAsyncHandler();
		
		
		/** Here we instantiate 3 TaskRequest instances */
		TaskRequest readDevicePropertiesTask = new TaskRequest();
		TaskRequest writeDeviceTaskRequest = new TaskRequest();
		TaskRequest executeRebootTaskRequest = new TaskRequest();

		
		/** Build the 'read' TaskRequest with the following properties 
		 * - here we read properties from device to ARTIK Cloud*/
		readDevicePropertiesTask.dtid(Config.DEVICE_TYPE_ID);
		readDevicePropertiesTask.dids(Arrays.asList(Config.DEVICE_IDS));
		readDevicePropertiesTask.taskType("R");
		readDevicePropertiesTask.setProperty("deviceProperties.device");
		

		/** Create 'write' TaskRequest with the following properties 
		 * - here we write to the timezone property */
		writeDeviceTaskRequest.dtid(Config.DEVICE_TYPE_ID);
		writeDeviceTaskRequest.dids(Arrays.asList(Config.DEVICE_IDS));
		writeDeviceTaskRequest.taskType("W");
		writeDeviceTaskRequest.setProperty("deviceProperties.device.timezone");
		writeDeviceTaskRequest.taskParameters(new TaskParameters().value("America/Los_Angeles"));
		
		
		/** Create 'execute' TaskRequet with the following properties 
		 * - here we execute reboot on the device */
		executeRebootTaskRequest.dtid(Config.DEVICE_TYPE_ID);
		executeRebootTaskRequest.dids(Arrays.asList(Config.DEVICE_IDS));
		executeRebootTaskRequest.taskType("E");
		executeRebootTaskRequest.setProperty("deviceProperties.device.reboot");
				
		/** Make the async call for the 3 Tasks we created earlier */
		try {
			devicesManagementApi.createTasksAsync(readDevicePropertiesTask, taskAsyncCallback);
			devicesManagementApi.createTasksAsync(writeDeviceTaskRequest, taskAsyncCallback);
			devicesManagementApi.createTasksAsync(executeRebootTaskRequest, taskAsyncCallback);
		} catch (ApiException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/** Commented out demonstrating blocking calls for above */
		//TaskEnvelope taskEnvelope = devicesManagementApi.createTasks(readDevicePropertiesTask); 
		//TaskEnvelope taskEnvelope = devicesManagementApi.createTasks(writeDeviceTaskRequest); 
		//TaskEnvelope taskEnvelope = devicesManagementApi.createTasks(executeRebootTaskRequest); 

	}
	

	public static ApiCallback<TaskEnvelope> taskAsyncHandler() {
		
		return new ApiCallback<TaskEnvelope>() {
			
			@Override
			public void onDownloadProgress(long arg0, long arg1, boolean arg2) {
				// TODO Auto-generated method stub
			}
			
			
			/** failure handler */
			@Override
			public void onFailure(ApiException apiException, int arg1, Map<String, List<String>> headers) {
				
				System.out.println("ApiException:" + apiException);
				System.out.println("Additional error info:" + apiException.getResponseBody());
				System.out.println("Headers:" + headers);
				
			}
			
			/** success handler **/
			@Override
			public void onSuccess(TaskEnvelope response, int arg1, Map<String, List<String>> headers) {
				
				System.out.println("Response:" + response);
				System.out.println("Headers:" + headers);
				
			}
			
			@Override
			public void onUploadProgress(long arg0, long arg1, boolean arg2) {
				// TODO Auto-generated method stub
				
			}

			
		};
	
	}
	
}
