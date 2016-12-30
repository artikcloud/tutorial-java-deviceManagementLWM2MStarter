## Sample application interacting with <u>**ARTIK Cloud Device Management API**</u>: Create <u>Tasks</u> for connected <u>LWM2M Clients</u>

### <u>Introduction:</u>

- Overview of ARTIK Cloud <u>Device Management API</u> for creating <u>Tasks</u> with read/write/execute operations.
- Overview connecting with LWM2M client.
- Overview interaction between <u>Tasks</u> and <u>LWM2M client.</u>

##### **Task / Read**

Sample app creates a Task to <u>read</u> from all device properties of connected LWM2M client.

1. Task is scheduled to ARTIK Cloud for processing and all deviceProperties from client are stored in ARTIK Cloud.  
2. See **Supported Objects and Resources** section below for list of available <u>read</u> operations.

##### **Task / Write**

Sampe app creates a Task to <u>write</u> to a LWM2M connected client.

1. Task is scheduled to ARTIK Cloud for processing and updates value for <u>timezone</u> on both the LWM2M client and ARTIK Cloud deviceProperties.timezone.
2. See **Supported Objects and Resources** section below for list available <u>write</u> operations.

##### **Task / Execute**

Sample app creates a Task to <u>execute</u> on a LWM2M connected client.

1. Task is scheduled to ARTIK Cloud for processing and LWM2M client executes <u>reboot</u> operation.
2. See **Supported Objects and Resources** section below for list available <u>execute</u> operations.

### <u>Setup:</u>

- Clone this sample application if you haven't already and setup project in your favorite IDE.
- Clone and build the [ARTIK Cloud Java SDK](https://github.com/artikcloud/artikcloud-java) and import the libraries into your project.
- Clone and build LWM2M client application (this sample uses the C client provided below).
- Follow the <u>Overview</u> instructions provided below.

### <u>Overview:</u>

1. Create a Device Type (or use one you already own) from your [Developer Dashboard](#Resources).   Then Enable <u>[Device Management Properties](#Resources)</u> for your Device Type.   

   *Note: You can do this in the [Device Type Dashboard](#Resources)—> Select Your Device Type —> Select Device Management —> <u>Enable Device Properties</u>*

2. Build the LWM2M Client (this tutorial uses the C client provided below in the reference section) if you haven't already. 
   *Note: The ARTIK Cloud LWM2M Client is available in the resource section at the bottom of this documentation as well at the build instructions.*

3. Connect a device (create one, or use one you already own) of the Device Type you enabled earlier.   The device credentials are available to you in the device settings.

4. Using the device credentials obtained in previous step, connect with LWM2M Client.

   ```
   %> akc_client -n -u coaps://coaps-api.artik.cloud:5686 -d YOUR_DEVICE_ID -t YOUR_DEVICE_TOKEN
   ```

   #### Here are a few quick commands to familiarize with the LWM2M client.   

   Here's a sample format to to read from the device properties.   For example to <u>read</u> from **Object 3 / Resource 2**  —  <u>timezone property</u>.  

   ```
   client%> read /3/0/15
   URI: /3/0/2 - Value: Pacific/California
   ```

   You can update a property in similar fashion

   ```
   client%> change /3/0/15 Paris/France
   ```

   *See the **Support Objects and Resources** section below* for a full table of <u><u>objects and resources numbers</u>.

5. In the sample code - find the Config.java file and fill in the following items:

   #### Update Config.java file

   1. <u>Device_Type_Id</u> — Device Type with Device Type Property Enabled

   2. <u>Device_Id</u> —  Device Id of your device

   3. <u>Device_Token</u> — Device Token of your device

      *The <u>Device Id,</u> <u>Device Token</u> and <u>Device Type Id</u> are available in the [User Portal](#resources) —> Device Settings.   Generate the token in the settings page if you haven't already.*

      ​

   4. <u>User_Token</u> — User Token that of user that owns the device.   

      *The User Access Token is obtained by OAuth2.   For convenience you can retrieve a user token by logging into the [API-Console](#Resources).  After logging in — make any request (ie:  /users/self) endpoint and use the Bearer Token shown in the Request Headers:*

      ```
      {
          "Content-Type": "application/json",
          "Authorization": "Bearer YOUR_ACCESS_TOKEN"
      }
      ```

6. Run the sample code by running the QuickStartTasks.java starter file.  The sample application makes a couple Task Requests to ARTIK Cloud for processing.

   #### Here's the sample TaskRequest that was used:

   ```java
   DevicesManagementApi devicesManagementApi = new DevicesManagementApi();
   TaskRequest writeDeviceTaskRequest = new TaskRequest();
   		writeDeviceTaskRequest.dtid(Config.DEVICE_TYPE_ID);
   		writeDeviceTaskRequest.dids(dids);
   		writeDeviceTaskRequest.taskType("W");
   		writeDeviceTaskRequest.setProperty("deviceProperties.device.timezone");
   		writeDeviceTaskRequest.taskParameters(new TaskParameters().value("Pacific/California"));
   devicesManagementApi.createTasksAsync(writeDeviceTaskRequest, callback);
   ```

   #### Here's a sample response after creating a Write operation to update the <u>timezone</u> property.

   ```javascript
   //From callback success 

   TaskEnvelope:class TaskEnvelope {
   data: class Task {
       filter: null
       taskType: W
       modifiedOn: 1482279165339
       dtid: <REDACTED-DEVICE-TYPE-ID>
       statusCounts: class TaskStatusCounts {
           numFailed: 0
           numCancelled: 0
           totalDevices: 0
           numCompleted: 0
           numSucceeded: 0
       }
       property: deviceProperties.device.timezone
       id: <REDACTED-TASK-ID>
       dids: [<REDACTED-DEVICE-ID>]
       taskParameters: class TaskParameters {
           expiresAfter: 604800
           value: Pacific/California
       }
       createdOn: 1482279165339
       status: REQUESTED
   }
   ```
   ##### Familiarize yourself with the following properties will help you build your TaskRequest:

- <u>taskType</u> - this can be either "R", "W", "E" (i.e.: Read, Write, Execute).    Check more about the resource operations that are available in the [table below](#Resources)
- <u>property</u> - dotted-json-notation string of the device or server property being worked on.
- <u>taskParameters.value</u> - value to 'set' on the <u>property</u> indicated.   
- <u>dtid</u> - device type id for device type enabled for device managment.
- <u>dids</u> - you may pass a list of device id's if you want to apply the task across multiple devices.

### <u>Supported Objects and Resources</u>

#### **<u>Object 3</u>**  —  <u>device</u> resources

| RESO URCE ID | NAME                        | ARTIK CLOUD NAME           | TYPE    | ARTIK CLOUD TYPE | INST ANCES | OPERAT IONS |
| :----------- | --------------------------- | -------------------------- | ------- | ---------------- | ---------- | ----------- |
| 0            | Manufacturer                | manufacturer               | String  | String           | Single     | R           |
| 1            | Model Number                | modelNumber                | String  | String           | Single     | R           |
| 2            | Serial Number               | serialNumber               | String  | String           | Single     | R           |
| 3            | Firmware Version            | firmwareVersion            | String  | String           | Single     | R           |
| 4            | Reboot                      | reboot                     |         |                  | Single     | E           |
| 5            | Factory Reset               | factoryReset               |         |                  | Single     | E           |
| 6            | Available Power Sources     | availablePower Sources     | Integer | Long             | Multiple   | R           |
| 7            | Power Source Voltage        | powerSource Voltage        | Integer | Long             | Multiple   | R           |
| 8            | Power Source Current        | powerSource Current        | Integer | Long             | Multiple   | R           |
| 9            | Battery Level               | batteryLevel               | Integer | Long             | Single     | R           |
| 10           | Memory Free                 | memoryFree                 | Integer | Long             | Single     | R           |
| 11           | Error Code                  | errorCode                  | Integer | Long             | Multiple   | R           |
| 12           | Reset Error Code            | resetErrorCode             |         |                  | Single     | E           |
| 13           | Current Time                | currentTime                | Time    | Long             | Single     | RW          |
| 14           | UTC Offset                  | utcOffset                  | String  | String           | Single     | RW          |
| 15           | Timezone                    | timezone                   | String  | String           | Single     | RW          |
| 16           | Supported Binding and Modes | supported Binding AndModes | String  | String           | Single     | R           |
| 17           | Device Type                 | deviceType                 | String  | String           | Single     | R           |
| 18           | Hardware Version            | hardwareVersion            | String  | String           | Single     | R           |
| 19           | Software Version            | softwareVersion            | String  | String           | Single     | R           |
| 20           | Battery Status              | batteryStatus              | Integer | Long             | Single     | R           |
| 21           | Memory Total                | memoryTotal                | Integer | Long             | Single     | R           |
| 22           | ExtDevInfo                  | *not supported*            |         |                  |            |             |

#### **<u>Object 5</u>** —  <u>firmwareUpdate</u> resources

| RESO URCE ID | NAME                     | ARTIK CLOUD NAME         | TYPE    | ARTIK CLOUD TYPE | INSTANCES | OPERAT IONS |
| ------------ | ------------------------ | ------------------------ | ------- | ---------------- | --------- | ----------- |
| 0            | Package                  | *not supported*          |         |                  |           |             |
| 1            | Package URI              | packageURI               | String  | String           | Single    | W           |
| 2            | Update                   | update                   |         |                  | Single    | E           |
| 3            | State                    | state                    | Integer | Long             | Single    | R           |
| 4            | Update Supported Objects | update Supported Objects | Boolean | Boolean          | Single    | RW          |
| 5            | Update Result            | updateResult             | Integer | Long             | Single    | R           |
| 6            | PkgName                  | pkgName                  | String  | String           | Single    | R           |
| 7            | PkgVersion               | pkgVersion               | String  | String           | Single    | R           |

This table of device resources are provided below grabbed from our [LWM2M documentation](https://developer.artik.cloud/documentation/advanced-features/manage-devices-using-lwm2m.html).



### <u>Resources</u>

###### Referenced Code Samples / Documentation

| Description                     | Type          | Source                                   |
| ------------------------------- | ------------- | ---------------------------------------- |
| ARTIK Cloud LWM2M Client (Java) | Code/  SDK    | https://github.com/artikcloud/artikcloud-lwm2m-java |
| ARTIK Cloud LWM2M Client (C)    | Code / SDK    | https://github.com/artikcloud/artikcloud-lwm2m-c |
| ARTIK Cloud SDK                 | Code / SDK    | https://github.com/artikcloud/artikcloud-java |
| Documentation: LWM2M            | Documentation | https://developer.artik.cloud/documentation/advanced-features/manage-devices-using-lwm2m.html) |
| Developer Dashboard:            | Dashboard     | https://developer.artik.cloud/dashboard  |
| User Portal                     | Dashboard     | https://my.artik.cloud                   |
| API Console                     | API-Console   | https://developer.artik.cloud/api-console/ |

### <u>Dashboards</u>

#### **User Portal** - https://my.artik.cloud

- **User Portal —> Devices**: for adding devices and generating device token

#### **Device Type Dashboard** - https://developer.artik.cloud/dashboard/devicetypes

- **Device Type —> Device Management —> Properties**:  Enable Server and Device Properties for Device Management
- **Device Type —> Device Management —> Tasks**:  View Task Status





*Internal Reference: /DX-754*