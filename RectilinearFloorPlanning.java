import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;  
import java.util.List;
import java.util.Scanner;

class Rectangle {
    int x, y, w, h;

    public Rectangle(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
}

public class RectilinearFloorPlanning {

    public static List<Rectangle> rectilinearFloorPlanning(int W, int H, List<Rectangle> rectangles) {
        // Sort rectangles based on area in descending order
        rectangles.sort(new RectangleComparator());

        // Initialize a list to store the positions of the placed rectangles
        List<Rectangle> placements = new ArrayList<>();

        for (Rectangle rectangle : rectangles) {
            // Try to place the rectangle in the available space
            boolean placed = false;

            for (int y = 0; y <= H - rectangle.h; y++) {
                for (int x = 0; x <= W - rectangle.w; x++) {
                    // Check if the current position is valid
                    boolean validPosition = isValidPosition(W, H, rectangle, x, y, placements);

                    if (validPosition) {
                        // Place the rectangle
                        rectangle.x += x;
                        rectangle.y += y;
                        placements.add(rectangle);
                        placed = true;
                        break;
                    }
                }

                if (placed) {
                    break;
                }
            }
        }

        return placements;
    }

    private static boolean isValidPosition(int W, int H, Rectangle rectangle, int x, int y, List<Rectangle> placements) {
        for (Rectangle r : placements) {
            if ((x + rectangle.x < r.x + r.w) &&
                (x + rectangle.x + rectangle.w > r.x) &&
                (y + rectangle.y < r.y + r.h) &&
                (y + rectangle.y + rectangle.h > r.y)) {
                return false;
            }
        }

        return 0 <= x + rectangle.x && x + rectangle.x < W &&
               0 <= y + rectangle.y && y + rectangle.y < H;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // User input for large rectangle dimensions
        System.out.print("Enter the width of the large rectangle: ");
        int W = scanner.nextInt();

        System.out.print("Enter the height of the large rectangle: ");
        int H = scanner.nextInt();

        // User input for rectangles
        System.out.print("Enter the number of rectangles: ");
        int numRectangles = scanner.nextInt();
        List<Rectangle> rectangles = new ArrayList<>();

        for (int i = 0; i < numRectangles; i++) {
            System.out.print("Enter the x-coordinate of rectangle " + (i + 1) + ": ");
            int x = scanner.nextInt();

            System.out.print("Enter the y-coordinate of rectangle " + (i + 1) + ": ");
            int y = scanner.nextInt();

            System.out.print("Enter the width of rectangle " + (i + 1) + ": ");
            int w = scanner.nextInt();

            System.out.print("Enter the height of rectangle " + (i + 1) + ": ");
            int h = scanner.nextInt();

            rectangles.add(new Rectangle(x, y, w, h));
        }

        // Solve the rectilinear floor-planning problem
        List<Rectangle> placements = rectilinearFloorPlanning(W, H, rectangles);

        // Display the results
        for (int i = 0; i < placements.size(); i++) {
            Rectangle rectangle = placements.get(i);
            System.out.println("Rectangle " + (i + 1) +
                    ": (x=" + rectangle.x + ", y=" + rectangle.y +
                    ", w=" + rectangle.w + ", h=" + rectangle.h + ")");
        }
    }
}

class RectangleComparator implements Comparator<Rectangle> {
    @Override
    public int compare(Rectangle r1, Rectangle r2) {
        return Integer.compare(r2.w * r2.h, r1.w * r1.h);
    }
}
