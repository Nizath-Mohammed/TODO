package com.todo.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.todo.model.Todo;
import com.todo.util.DatabaseConnection;

public class TodoAppDAO {
    private static final String SELECT_ALL_TODOS =
            "SELECT * FROM todos ORDER BY created_at DESC";

    private static final String INSERT_TODO =
            "INSERT INTO todos (title, description, completed, created_at, updated_at) VALUES (?,?,?,?,?)";

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

    // Fetch all todos
    public List<Todo> getAllTodos() throws SQLException {
        List<Todo> todos = new ArrayList<>();
        DatabaseConnection db = new DatabaseConnection();

        try (Connection conn = db.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_TODOS);
             ResultSet res = stmt.executeQuery()) {

            while (res.next()) {
                Todo obj = getTodoRow(res);
                todos.add(obj);
            }
        }
        return todos;
    }

    // Insert new todo and return generated ID
    public int createTodo(Todo todo) throws SQLException {
        DatabaseConnection db = new DatabaseConnection();

        try (Connection cn = db.getDBConnection();
             PreparedStatement stmt = cn.prepareStatement(INSERT_TODO, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, todo.getTitle());
            stmt.setString(2, todo.getDescription());
            stmt.setBoolean(3, todo.isCompleted());
            stmt.setTimestamp(4, Timestamp.valueOf(todo.getCreated_at()));
            stmt.setTimestamp(5, Timestamp.valueOf(todo.getUpdated_at()));

            int rowAffected = stmt.executeUpdate();
            if (rowAffected == 0) {
                throw new SQLException("Creating todo failed, no row was inserted.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating todo failed, no ID obtained.");
                }
            }
        }
    }
}
