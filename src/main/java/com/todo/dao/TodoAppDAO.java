package com.todo.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.todo.model.Todo;
import com.todo.util.DatabaseConnection;

public class TodoAppDAO {
    private static final String SELECT_ALL_TODOS="SELECT * FROM todos ORDER BY created_at DESC";

    // helper method to convert ResultSet row -> Todo object
    private Todo getTodoRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String description = rs.getString("description");
        boolean completed = rs.getBoolean("completed");
        LocalDateTime created_at = rs.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updated_at = rs.getTimestamp("updated_at").toLocalDateTime();

        return new Todo(id, title, description, completed, created_at, updated_at);
    }

    public List<Todo> getAllTodos() throws SQLException {
        List<Todo> todos = new ArrayList<>();
        DatabaseConnection db = new DatabaseConnection();

        try (Connection conn = db.getDBConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    SELECT_ALL_TODOS
            );
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                Todo obj = getTodoRow(res);
                todos.add(obj);
            }
        }

        return todos;
    }

    /* here a todo represents the object of the model class */
}
