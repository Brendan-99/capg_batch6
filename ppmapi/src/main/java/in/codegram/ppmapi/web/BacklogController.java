package in.codegram.ppmapi.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import in.codegram.ppmapi.domain.ProjectTask;
import in.codegram.ppmapi.service.MapValidationErrorService;
import in.codegram.ppmapi.service.ProjectService;
import in.codegram.ppmapi.service.ProjectTaskService;

@RestController
@RequestMapping("/api/backlog")
public class BacklogController {
	
	@Autowired
	private ProjectTaskService projectTaskService;
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService; 
	
	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlog_id){
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationError(result);
		if(errorMap!=null) return errorMap;
		ProjectTask savedProjectTask = projectTaskService.addProjectTask(backlog_id, projectTask);
		return new ResponseEntity<ProjectTask>(savedProjectTask, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id,@PathVariable String pt_id){
		ProjectTask projectTask = projectTaskService.findPTByProjectSequence(backlog_id, pt_id);
		return new ResponseEntity<ProjectTask>(projectTask,HttpStatus.OK);
		
	}
	
	@PatchMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask updatedTask,@PathVariable String backlog_id,@PathVariable String pt_id,BindingResult result ){
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationError(result);
		if(errorMap!=null) return errorMap;
		ProjectTask projectTask =  projectTaskService.updateByProjectSequence(updatedTask, backlog_id, pt_id);
		return new ResponseEntity<ProjectTask>(projectTask,HttpStatus.OK);
	}
	@DeleteMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id,@PathVariable String pt_id){
		projectTaskService.deletePTByProjectSequence(backlog_id, pt_id);
		return new ResponseEntity<String>("Project Task '"+pt_id.toUpperCase()+"' is deleted successfully!",HttpStatus.OK);
	}

}
