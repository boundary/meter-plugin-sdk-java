Boundary Java Plugin SDK
========================




## Collectors

### JMX
JMX or *Java Management Extentions* is

The JMX Collector is a confguration driven JMX client that polls a java virtual machine to collect peformance metrics.

#### `mbeans.json`
The `mbeans.json` configuration file is the core configration file that drives the MBeans collection process.

An example configuration file is shown below and with a description of each of the fields .

```json
{
  "map": [
    {
      "mbean": "java.lang:name=Metaspace,type=MemoryPool",
      "attributes": [
        {
          "attribute": "Usage",
          "dataType": "javax.management.openmbean.CompositeData",
          "metricName": "JAVA.LANG.MEMORYPOOL.METASPACE.USAGE",
          "key": "used",
          "metricType": "standard",
          "scale": 1,
          "enabled": true
        }
        ],
      "enabled": true
    }
}
```

#### `mbean`
Name of an MBean to collect metrics from

#### `enabled`

 
#### `attributes`
List of specific MBean attributes which contain values to be collected

##### `dataType`
Java data type of the attribute

##### `metricName`
Boundary metric name to be associated with the attribute value

##### `Scale`
Scaling factor to be applied to metric value

##### `enabled`
