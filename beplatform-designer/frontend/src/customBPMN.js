import BpmnJS from "./Bpmn.js";
var bpmnModeler;


/**
 * Save diagram contents and print them to the console.
 */
async function exportDiagram() {

	try {

		var result = await bpmnModeler.saveXML({ format: true });

		alert('Diagram exported. Check the developer tools!');

		console.log('DIAGRAM', result.xml);
	} catch (err) {

		console.error('could not save BPMN 2.0 diagram', err);
	}
}

/**
 * Open diagram in our modeler instance.
 *
 * @param {String} bpmnXML diagram to display
 */
async  function openDiagram(bpmnXML) {

	// import diagram
	try {
		await bpmnModeler.importXML(bpmnXML);
		// access modeler components
		var canvas = bpmnModeler.get('canvas');
		var overlays = bpmnModeler.get('overlays');

		// zoom to fit full viewport
		canvas.zoom('fit-viewport');
		// attach an overlay to a node
		overlays.add('SCAN_OK', 'note', {
			position: {
				bottom: 0,
				right: 0
			},
			html: '<div class="diagram-note">Mixed up the labels?</div>'
		});
		// add marker
		canvas.addMarker('SCAN_OK', 'needs-discussion');
	} catch (err) {

	}
}



window.loadScreen = function(){
	exportDiagram();
}


window.openDiagram = function(){


	try{
		document.getElementById("canvas").innerHTML = "";
		bpmnModeler = new BpmnJS({
			container: '#canvas',
			keyboard: {
				bindTo: window
			}
		});

		openDiagram('<?xml version="1.0" encoding="UTF-8"?><bpmn:definitions xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" id="Definitions_1176k0r" targetNamespace="http://bpmn.io/schema/bpmn" exporter="bpmn-js (https://demo.bpmn.io)" exporterVersion="8.5.0"><bpmn:collaboration id="Collaboration_1sdem6v"><bpmn:participant id="Participant_1iy05jm" processRef="Process_1s66mtb" /></bpmn:collaboration><bpmn:process id="Process_1s66mtb" isExecutable="false" /><bpmndi:BPMNDiagram id="BPMNDiagram_1"><bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="Collaboration_1sdem6v"><bpmndi:BPMNShape id="Participant_1iy05jm_di" bpmnElement="Participant_1iy05jm" isHorizontal="true"><dc:Bounds x="160" y="60" width="600" height="250" /></bpmndi:BPMNShape></bpmndi:BPMNPlane></bpmndi:BPMNDiagram></bpmn:definitions>');

		var eventBus = bpmnModeler.get("eventBus");

		eventBus.on("element.click", function(event) {

			console.log(event.element.type);
			if(event.element.type === "bpmn:StartEvent"){

				document.getElementById("btnUsrStg").click();

				var stgID =  document.getElementById("bpmnUsrStgID");
				var stgName = document.getElementById("bpmnUsrStgName");

				if(typeof stgID !== null && stgID !== 'undefined' && stgID !== null && stgName !== null  && typeof stgName !== null && stgName !== 'undefined'){

					document.getElementById("bpmnUsrStgID").style.visibility = "hidden"; 
					document.getElementById("bpmnUsrStgName").style.visibility = "hidden"; 
					document.getElementById("bpmnUsrStgID").innerHTML = event.element.id;
					document.getElementById("bpmnUsrStgName").innerHTML = event.element.type;
				}

			}
			if(event.element.type === "bpmn:Task"){

				document.getElementById("btnSysStg").click();

				var stgID =  document.getElementById("bpmnSysStgID");
				var stgName = document.getElementById("bpmnSysStgName");

				if(typeof stgID !== null && stgID !== 'undefined' && stgID !== null && stgName !== null  && typeof stgName !== null && stgName !== 'undefined'){
					document.getElementById("bpmnSysStgID").innerHTML = event.element.id;
					document.getElementById("bpmnSysStgName").innerHTML = event.element.type;
					document.getElementById("bpmnSysStgID").style.visibility = "hidden"; 
					document.getElementById("bpmnSysStgName").style.visibility = "hidden"; 
				}


			}


		});

	}catch(e){
		console.error('error in load bpmn module', e);
	}
}

