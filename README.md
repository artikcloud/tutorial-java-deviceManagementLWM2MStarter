# Java DEVICE MANAGEMENT Starter Code

tutorial-java-deviceManagementStarterCode

This sample will read and write to your custome device server properties using the [ARTIK Cloud Java SDK](https://github.com/artikcloud/artikcloud-java).

### Prerequisite
* Java >= 7
* ARTIK Cloud Java SDK  >= 2.0.6

### Setup / Installation:

Before running code, first enable **device management** on your **device type**:  (https://developer.artik.cloud/documentation/advanced-features/device-management.html)

Here is a quick outline of steps:

1. git clone this project and [import the ARTIK Cloud Java SDK](https://github.com/artikcloud/artikcloud-java)
2. In developer portal: enable **device management properties** for a **device type** you own.
3. Add server properties: in this example, we added 3 server properties called:  name, enabled, and random of type <String>, <Boolean>, <Integer> respectively.
4. In MyServerProperty.java: add the same **server properties** as member variable names and matching data type from step 2.
5. Go to My ARTIK Cloud and **connect device** (of your **device type**) and retrieve your Device ID and Device Token.
6. Set your **device id** and **device token** in **Config.java** file.
7. Run code.

## Run the code

Run the QuickStartServerProperties.java file.

Here's a **sample output** after running the program which demonstrates reading and updating the custom device server properties made available setup/installation step: "name, enabled, and random".

```java
Called 1: getProperties()===>
Result class MetadataEnvelope {
    data: {serverProperties={name=e4028c51-cbb4-44da-9d59-c3382f9f50b8, enabled=false, random=73.0}, systemProperties={did=a167597eb86e41ffbecaf1e39ca4cdd5, connection=null}, deviceProperties={}}
}

Called 1: updateServerProperties()===>
Result class MetadataEnvelope {
    data: {did=a167597eb86e41ffbecaf1e39ca4cdd5}
}

Called 1: resultAfterUpate()===>
Result class MetadataEnvelope {
    data: {serverProperties={name=0baf9598-f953-4251-8cc5-a4c81f82e50c, enabled=false, random=86.0}, systemProperties={did=a167597eb86e41ffbecaf1e39ca4cdd5, connection=null}, deviceProperties={}}
}

Async: Async callback===>>>>class MetadataEnvelope {
    data: {serverProperties={name=0baf9598-f953-4251-8cc5-a4c81f82e50c, enabled=false, random=86.0}, systemProperties={did=a167597eb86e41ffbecaf1e39ca4cdd5, connection=null}, deviceProperties={}}
}

Async: Async callback===>>>>class MetadataEnvelope {
    data: {did=a167597eb86e41ffbecaf1e39ca4cdd5}
}
```

## Peek into the implementation

Take a closer look at the following files:
* /src/Config.java
* /src/MyServerProperty.java
* /src/QuickStartServerProperties.java 

**Config.java**

Fill in your Device ID and Device Token after creating an instance of your device.

```java
public class Config {
	
	public static final String DEVICE_ID = "Your Device Id";
	public static final String DEVICE_TOKEN = "Your Device Token";
	
}

```

**MyServerProperty.java**

Define member variables that match the server properties you have defined.    These values are used to serialize your data to JSON.

```java
public class MyServerProperty {

	//add your custom server properties below to match
	//the server properties you've added for your device type

	String name = "untitled";
	Boolean enabled = true;
	Integer random = 0;

	public MyServerProperty(String name, Boolean enabled, Integer number) {
		
		this.name = name;
		this.enabled = enabled;
		this.random = number;
		
	}
}
```

**QuickStart.java**

Snip for reading and updating Server Properties.

Note that we pass the custom class MyServerProperty instance into **updateServerProperties** which will be JSON serialized with your values.

```java
//batch api calls 1
devicesManagementApi.getProperties(Config.DEVICE_ID, includeTimestamp);
devicesManagementApi.updateServerProperties(Config.DEVICE_ID, 
				new MyServerProperty("My Server Property", false, (int) Math.round(Math.random()*100)));
devicesManagementApi.getProperties(Config.DEVICE_ID, includeTimestamp);
```



## Device Management Resources

[Devices Management Java SDK API](https://github.com/artikcloud/artikcloud-java/blob/master/docs/DevicesManagementApi.md) 

[Devices Management REST API](https://developer.artik.cloud/documentation/api-reference/rest-api.html#device-management)

[Devices Management LWM2M - Java Client](https://github.com/artikcloud/artikcloud-lwm2m-java)

[Devices Management LWM2M - C Client](https://github.com/artikcloud/artikcloud-lwm2m-c)


More about ARTIK Cloud
---------------------

If you are not familiar with ARTIK Cloud, we have extensive documentation at https://developer.artik.cloud/documentation

The full ARTIK Cloud API specification can be found at https://developer.artik.cloud/documentation/api-reference/

Peek into advanced sample applications at https://developer.artik.cloud/documentation/samples/

To create and manage your services and devices on ARTIK Cloud, visit the Developer Dashboard at https://developer.artik.cloud


License and Copyright
---------------------

Licensed under the Apache License. See [LICENSE](LICENSE).

Copyright (c) 2016 Samsung Electronics Co., Ltd.

