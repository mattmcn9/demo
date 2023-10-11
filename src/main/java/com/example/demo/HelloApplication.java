package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    private List<Color> gradientColors = new ArrayList<>();
    private Rectangle rec;
    private Rectangle gradientDisplay;
    private Canvas gradientWheel;
    private final double WHEEL_SIZE = 200;
    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        gradientDisplay = new Rectangle(500, 50);
        updateGradientDisplay();
        Rectangle colorPreview = new Rectangle(50, 50);
        gradientWheel = new Canvas(WHEEL_SIZE, WHEEL_SIZE);
        createGradientWheel(gradientWheel);

        gradientWheel.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            // 1. Pick the color
            Color selectedColor = pickColorFromWheel( event.getX(), event.getY());

            // 2. Update the color preview
            colorPreview.setFill(selectedColor);

            // 3. Draw the marker
            drawMarker(event.getX(), event.getY());
        });


        gradientWheel.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            if (event.getX() >= 0 && event.getX() <= WHEEL_SIZE && event.getY() >= 0 && event.getY() <= WHEEL_SIZE) {
                // 1. Pick the color
                Color selectedColor = pickColorFromWheel( event.getX(), event.getY());

                // 2. Update the color preview
                colorPreview.setFill(selectedColor);

                // 3. Draw the marker
                drawMarker(event.getX(), event.getY());
            }
        });




//        gradientWheel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//            colorPreview.setFill(pickColorFromWheel(gradientWheel, event.getX(), event.getY()));
//        });

        Button addButton = new Button("Add to Gradient");
        addButton.setOnAction(e -> {
            gradientColors.add((Color) colorPreview.getFill());
            updateGradientDisplay();
        });

        HBox controls = new HBox(10, gradientWheel, colorPreview, addButton);

        root.getChildren().addAll(gradientDisplay, controls);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Gradient Builder");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void drawMarker(double x, double y) {
        double markerRadius = 5;
        GraphicsContext gc = gradientWheel.getGraphicsContext2D();

        // Redraw the gradient for the whole canvas to clear the old marker
        createGradientWheel(gradientWheel);

        // Draw the marker at the new position
        gc.setFill(Color.BLACK);
        gc.fillOval(x - markerRadius, y - markerRadius, markerRadius * 2, markerRadius * 2);
    }


    private void createGradientWheel(Canvas canvas) {
        double size = canvas.getWidth();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double centerX = size / 2;
        double centerY = size / 2;

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                double dx = x - centerX;
                double dy = y - centerY;

                double distance = Math.sqrt(dx * dx + dy * dy);
                double angle = Math.atan2(dy, dx) / (2 * Math.PI) + 0.5;

                if (distance <= size / 2) {
                    gc.getPixelWriter().setColor(x, y, Color.hsb(angle * 360, 1, 1));
                }
            }
        }
    }

    private Color pickColorFromWheel( double x, double y) {
        WritableImage image = gradientWheel.snapshot(null, null);
        PixelReader pixelReader = image.getPixelReader();
        return pixelReader.getColor((int) x, (int) y);
    }

    private void updateGradientDisplay() {
        Stop[] stops = new Stop[gradientColors.size()];
        for (int i = 0; i < gradientColors.size(); i++) {
            stops[i] = new Stop(i / (double) (gradientColors.size() - 1), gradientColors.get(i));
        }

        if (gradientColors.size() == 1) {
            gradientDisplay.setFill(gradientColors.get(0));
        } else if (gradientColors.size() > 1) {
            gradientDisplay.setFill(new LinearGradient(0, 0, 1, 0, true, null, stops));
        } else {
            gradientDisplay.setFill(Color.TRANSPARENT);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class GradientConverter {
    
    // Example structure of GlimpsesGradient. 
    // Modify according to the actual structure.
    static class GlimpsesGradient {
        float startX, startY, endX, endY;
        String[] colors; 
        float[] fractions;
        
        GlimpsesGradient(float startX, float startY, float endX, float endY, String[] colors, float[] fractions) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
            this.colors = colors;
            this.fractions = fractions;
        }
    }
    
    public static LinearGradient convertGlimpsesToJavaFX(GlimpsesGradient glimpsesGradient) {
        Stop[] stops = new Stop[glimpsesGradient.colors.length];
        
        for (int i = 0; i < glimpsesGradient.colors.length; i++) {
            stops[i] = new Stop(glimpsesGradient.fractions[i], javafx.scene.paint.Color.web(glimpsesGradient.colors[i]));
        }
        
        return new LinearGradient(glimpsesGradient.startX, glimpsesGradient.startY, glimpsesGradient.endX, glimpsesGradient.endY, false, javafx.scene.paint.CycleMethod.NO_CYCLE, stops);
    }
    
    public static void main(String[] args) {
        GlimpsesGradient gradient = new GlimpsesGradient(0f, 0f, 1f, 0f, new String[]{"#ff0000", "#0000ff"}, new float[]{0f, 1f});
        LinearGradient fxGradient = convertGlimpsesToJavaFX(gradient);
        
        System.out.println(fxGradient);
    }
}




