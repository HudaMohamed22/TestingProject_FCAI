package com.fcai.SoftwareTesting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcai.SoftwareTesting.todo.TodoCreateRequest;
import com.fcai.SoftwareTesting.todo.TodoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestTodoController {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testCreateTodo() throws Exception {
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest();
        todoCreateRequest.setTitle("Test Todo");
        todoCreateRequest.setDescription("Test Todo1");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString(todoCreateRequest);

        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }
    @Test
    public void testCreateTodo1() throws Exception {//testing the bad request exception
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest();
        todoCreateRequest.setTitle("Test Todo");
        todoCreateRequest.setDescription("Test Todo1");

        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"\", \"description\":\"Hello11\"}"))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testReadTodo() throws Exception {
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest();
        todoCreateRequest.setTitle("Test Todo");
        todoCreateRequest.setDescription("Test Todo1");
        TodoServiceImpl todoServiceImpl=new TodoServiceImpl();
        todoServiceImpl.create(todoCreateRequest);
        mockMvc.perform(get("/read")
                        .param("id", "1"))
                .andExpect(status().isOk());
    }
    @Test
    public void testReadTodo1() throws Exception {//testing the bad request exception

        mockMvc.perform(get("/read")
                        .param("id", "1"))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testupdateTodo() throws Exception {
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest();
        todoCreateRequest.setTitle("Test Todo");
        todoCreateRequest.setDescription("Test Todo1");
        TodoServiceImpl todoServiceImpl=new TodoServiceImpl();
        todoServiceImpl.create(todoCreateRequest);
        mockMvc.perform(put("/update")
                        .param("id", "1").param("completed", String.valueOf(true)))
                .andExpect(status().isOk());
    }
    @Test
    public void testupdateTodo1() throws Exception {//testing the bad request exception

        mockMvc.perform(put("/update")
                        .param("id", "1111").param("completed", String.valueOf(true)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testdeleteTodo() throws Exception {
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest();
        todoCreateRequest.setTitle("Test Todo");
        todoCreateRequest.setDescription("Test Todo1");
        TodoServiceImpl todoServiceImpl=new TodoServiceImpl();
        todoServiceImpl.create(todoCreateRequest);
        mockMvc.perform(delete("/delete")
                        .param("id", "1"))
                .andExpect(status().isOk());
    }
    @Test
    public void testdeleteTodo1() throws Exception {//testing the bad request exception

        mockMvc.perform(delete("/delete")
                        .param("id", "3"))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testlistTodo() throws Exception {

        mockMvc.perform(get("/list")
                       )
                .andExpect(status().isOk());
    }

    @Test
    public void testlistCompletedTodo() throws Exception {

        mockMvc.perform(get("/listCompleted")
                       )
                .andExpect(status().isOk());
    }
}
