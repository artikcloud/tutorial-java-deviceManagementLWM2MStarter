package quickstart.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cloud.artik.api.DevicesManagementApi;
import cloud.artik.client.ApiCallback;
import cloud.artik.client.ApiClient;
import cloud.artik.client.ApiException;
import cloud.artik.model.TaskEnvelope;
import cloud.artik.model.TaskParameters;
import cloud.artik.model.TaskRequest;
import cloud.artik.model.TaskStatusesEnvelope;
import quickstart.devicemanagment.ClientHelper;
import quickstart.devicemanagment.Config;

public class QuickStartTasks {
	
	public static void main (String args[]) throws ApiException {
	
		// User Token is required for this sample.
		ApiClient apiClient = null;
		ClientHelper.initClient(apiClient, Config.USER_TOKEN);
		
		DevicesManagementApi devicesManagementApi = new DevicesManagementApi();
		Boolean includeTimestamp = false;
		
		//Callback for calling TaskRequest Async sample calls
		ApiCallback<TaskEnvelope> callback = new ApiCallback<TaskEnvelope>() {

			@Override
			public void onDownloadProgress(long arg0, long arg1, boolean arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onFailure(ApiException arg0, int arg1, Map<String, List<String>> arg2) {
				// TODO Auto-generated method stub
				
				System.out.println("From callback error:" + arg0);
				System.out.println("Additional error info:" + arg0.getResponseBody());
				System.out.println("Failure arg2:" + arg2);
				
			}

			@Override
			public void onSuccess(TaskEnvelope arg0, int arg1, Map<String, List<String>> arg2) {
				// TODO Auto-generated method stub
				
				System.out.println("From callback success TaskEnvelope:" + arg0);
				System.out.println("===>Addtional Success data:" + arg0.getData());
				System.out.println("Success arg2:" + arg2);
				
			}

			@Override
			public void onUploadProgress(long arg0, long arg1, boolean arg2) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		//add devices the task will operate on
		List<String> dids = new ArrayList<String>();
		dids.add(Config.DEVICE_ID);
		
		//read
		TaskRequest readTask = new TaskRequest();
		readTask.dtid(Config.DEVICE_TYPE_ID);
		readTask.dids(dids);
		readTask.taskType("R");
		readTask.setProperty("deviceProperties.device");  		//set to read all properties from device
		devicesManagementApi.createTasksAsync(readTask, callback);  // async sample call
		TaskEnvelope taskEnvelope = devicesManagementApi.createTasks(readTask);  //creates the task
		System.out.println(taskEnvelope);

	
		//view the task status
		//getStatus(taskid, count, offset, status, deviceid)
		TaskStatusesEnvelope taskStatusEnvelope = devicesManagementApi
				.getStatuses(taskEnvelope.getData().getId(), 100, 0, "REQUESTED", Config.DEVICE_ID);
		
		System.out.println("Status Envelope:" + taskStatusEnvelope);
		
		//task write operation sample
		TaskRequest writeDeviceTaskRequest = new TaskRequest();
		writeDeviceTaskRequest.dtid(Config.DEVICE_TYPE_ID);
		writeDeviceTaskRequest.dids(dids);
		writeDeviceTaskRequest.taskType("W");
		writeDeviceTaskRequest.setProperty("deviceProperties.device.timezone");
		writeDeviceTaskRequest.taskParameters(new TaskParameters().value("Pacific/California"));
		
		devicesManagementApi.createTasksAsync(writeDeviceTaskRequest, callback);
		
		
		//task execute operation sample
		TaskRequest rebootTaskRequest = new TaskRequest();
		rebootTaskRequest.dtid(Config.DEVICE_TYPE_ID);
		rebootTaskRequest.dids(dids);
		rebootTaskRequest.taskType("E");
		rebootTaskRequest.setProperty("deviceProperties.device.reboot");
		devicesManagementApi.createTasksAsync(rebootTaskRequest, callback);
	}

}
