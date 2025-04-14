package edu.hw2;

public class Task2 {
    public static class Rectangle {
        protected final int width;
        protected final int height;

        public Rectangle(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public final int getWidth() {
            return width;
        }

        public final int getHeight() {
            return height;
        }

        double area() {
            return width * height;
        }

        public Rectangle withWidth(int newWidth) {
            return new Rectangle(newWidth, this.height);
        }

        public Rectangle withHeight(int newHeight) {
            return new Rectangle(this.width, newHeight);
        }
    }

    public static class Square extends Rectangle {

        public Square(int side) {
            super(side, side);
        }

        @Override
        public Rectangle withWidth(int newSide) {
            return new Square(newSide);
        }

        @Override
        public Rectangle withHeight(int newSide) {
            return new Square(newSide);
        }
    }
}
