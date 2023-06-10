package com.fcai.SoftwareTesting;

import com.fcai.SoftwareTesting.todo.Todo;
import com.fcai.SoftwareTesting.todo.TodoCreateRequest;
import com.fcai.SoftwareTesting.todo.TodoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestTodoServiceImpl
{
    TodoServiceImpl todoServiceImpl;
    public void  arrange(){
         this.todoServiceImpl =new TodoServiceImpl();
    }
    @Test
    public void TestTodocreate1(){//test when creat method gets a null object
        TodoCreateRequest todoCreateRequest = null;
        arrange();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> todoServiceImpl.create(todoCreateRequest));
        Assertions.assertEquals("Todo cannot be null",exception.getMessage());
    }
    @Test
    public void TestTodocreate2(){//test when creat method gets an empty title

        TodoCreateRequest todoCreateRequest = new TodoCreateRequest("","description");
        arrange();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> todoServiceImpl.create(todoCreateRequest));
        Assertions.assertEquals("Todo title cannot be empty",exception.getMessage());
    }
    @Test
    public void TestTodocreate3(){//test when creat method gets an empty description

        TodoCreateRequest todoCreateRequest = new TodoCreateRequest("title","");
        arrange();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> todoServiceImpl.create(todoCreateRequest));
        Assertions.assertEquals("Todo description cannot be empty",exception.getMessage());
    }
    @Test
    public void TestTodoRead(){//test when read method gets a null id

        arrange();
        String id= null;
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> todoServiceImpl.read(id));
        Assertions.assertEquals("Todo id cannot be null",exception.getMessage());
    }
    @Test
    public void TestTodoRead1(){//test when read method gets an empty id

        arrange();
        String id="";
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> todoServiceImpl.read(id));
        Assertions.assertEquals("Todo id cannot be empty",exception.getMessage());
    }

    @Test
    public void TestTodoUpdate1(){//test creat methode ,read method ,and update method at the same time

        arrange();
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest("MyTitle","MyDescription");
        todoServiceImpl.create(todoCreateRequest);
        String id= "1";
        todoServiceImpl.update(id,false);

        Todo newTodo = new Todo();
        newTodo.setCompleted(false);
        newTodo.setId(id);
        newTodo.setDescription("MyDescription");
        newTodo.setTitle("MyTitle");

        Assertions.assertEquals(newTodo.getDescription(),todoServiceImpl.read(id).getDescription());
        Assertions.assertEquals(newTodo.getTitle(),todoServiceImpl.read(id).getTitle());
        Assertions.assertEquals(newTodo.getId(),todoServiceImpl.read(id).getId());
        Assertions.assertEquals(newTodo.isCompleted(),todoServiceImpl.read(id).isCompleted());
    }
    @Test
    public void TestTodoDelete(){//test creat methode and delete method
//test when read method gets a not exist id
        arrange();
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest("MyTitle","MyDescription");
        todoServiceImpl.create(todoCreateRequest);
        String id= "1";
        todoServiceImpl.delete(id);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> todoServiceImpl.read(id));
        Assertions.assertEquals("Todo not found",exception.getMessage());


    }
    @Test
    public void TestTodolist(){//test list method
        arrange();
        todoServiceImpl.MakeTodoListNull();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> todoServiceImpl.list());
        Assertions.assertEquals("Todo list cannot be null",exception.getMessage());
    }
    @Test
    public void TestTodolist1(){//test list method ,and test setting the id in the creat method
        arrange();
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest("MyTitle","MyDescription");
        TodoCreateRequest todoCreateRequest1 = new TodoCreateRequest("MyTitle","MyDescription");
        TodoCreateRequest todoCreateRequest3 = new TodoCreateRequest("MyTitle","MyDescription");
        todoServiceImpl.create(todoCreateRequest);
        todoServiceImpl.create(todoCreateRequest1);
        todoServiceImpl.create(todoCreateRequest3);

        for (int i = 0; i < todoServiceImpl.list().size(); i++) {
            Assertions.assertEquals(Integer.toString(i+1),todoServiceImpl.list().get(i).getId());
        }
    }
    @Test
    public void TestTodolistCompleted(){//test listCompleted method
        arrange();
        todoServiceImpl.MakeTodoListNull();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> todoServiceImpl.listCompleted());
        Assertions.assertEquals("Todo list cannot be null",exception.getMessage());
    }
    @Test
    public void TestTodolistCompleted1(){//test listCompleted method
        int numberOfCompletedTodos=3;
        arrange();
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest("MyTitle","MyDescription");
        TodoCreateRequest todoCreateRequest1 = new TodoCreateRequest("MyTitle","MyDescription");
        TodoCreateRequest todoCreateRequest3 = new TodoCreateRequest("MyTitle","MyDescription");
        TodoCreateRequest todoCreateRequest4 = new TodoCreateRequest("MyTitle","MyDescription");

        todoServiceImpl.create(todoCreateRequest);
        todoServiceImpl.create(todoCreateRequest1);
        todoServiceImpl.create(todoCreateRequest3);
        todoServiceImpl.create(todoCreateRequest4);
        for (int i = 0; i < numberOfCompletedTodos; i++) {
            todoServiceImpl.update(Integer.toString(i+1),true);
        }
        Assertions.assertEquals(numberOfCompletedTodos,todoServiceImpl.listCompleted().size());
        for (int i = 0; i < todoServiceImpl.listCompleted().size(); i++) {
            Assertions.assertEquals(true,todoServiceImpl.listCompleted().get(i).isCompleted());
        }

    }



}
