This is the ETL module for the enviroCar server
 it comprises of three main compoments
 <br/>
 <br/>

<div bgcolor="grey">
 -> The source cloner <br/>
 -> The datasetdump   <br/>
 -> The destination Loader <br/>
 </div> <br/> <br/> <br/>

<b> Steps for Running module </b>

<ul>
<li>unzip the <a href="https://github.com/gotodeepak1122/enviroCar-server/tree/master/etl/src/main/resources/EnviroCarTestData" >resources/envirocar.zip</a> file and load it onto your local mongodb instance using 'mongorestore -d enviroCar enviroCar' command </li>
<li>Place <a href ="https://github.com/gotodeepak1122/enviroCar-server/tree/master/etl/src/main/resources">fuseki war file</a> and upload the war file in tomcat and launch it</li>
<li>after starting tomcat go to <a href ="http://localhost:8080/fuseki">http://localhost:8080/fuseki</a> and using the user interface navigate to <em>manage datasets -> add new dataset</em> in the Dataset name type in 'envirocar' and choose the persisent store option </li>
<li>Clone the envirocar repo by 'git clone https://github.com/gotodeepak1122/enviroCar-server' into your local repository</li>
<li>navigate to the etl directory and execute 'mvn clean package'</li>
<li>Open any browser and type in the url <a href ="http://localhost:8080/fuseki">http://localhost:8080/fuseki</a> and experience the SPARQL endpoint  </li>
</ul>

<b>Tested with </b>
<ul>
<li>Java 1.7.0_75</li>
<li>MongoDB 2.12</li>
<li>maven 3.2.5</li>
</ul>

