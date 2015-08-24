This is the ETL module for the enviroCar server
 it comprises of three main compoments
 <br/>

<div bgcolor="grey">
 -> The source cloner <br/>
 -> The datasetdump   <br/>
 -> The destination Loader <br/>
 </div> <br/>
 
The  <a href="https://drive.google.com/file/d/0B_H46yHBn4pUVE1fa3Y0aGZva0U/view?usp=sharing">Path flow </a> diagram of how this module functions can be found here <br/>

<b> Steps for Running module </b>

<ul>
<li>unzip the <a href="https://github.com/gotodeepak1122/enviroCar-server/tree/master/etl/src/main/resources/EnviroCarTestData" >resources/envirocar.zip</a> file and load it onto your local mongodb instance using 'mongorestore -d enviroCar enviroCar'</li>
<li> mvn clean install </li>
<li> modify config.properties </li>
<li> java -jar etl/target/etl-snapshot.0.0.1.jar (ensure fuseki and mongo is running before)  </li>
</ul>

<b>Tested with </b>
<ul>
<li>Java 1.7.0_75</li>
<li>MongoDB 2.12</li>
<li>maven 3.2.5</li>
</ul>

