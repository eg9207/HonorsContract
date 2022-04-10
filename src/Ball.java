public class Ball {
    // PROPERTIES
    // Velocity
    public static double dblVel1;
    public static double dblVel1X;
    public static double dblVel1Y;

    public static double dblVel;
    public static double dblVelY;
    public static double dblCurrentAngle;

    // Distance
    public static double dblDistX;
    public static double dblDistY;
    public static double dblDistYMax;

    public static double dblAcceleration;
    public static final double dblGravity = 9.8;
    public static double dblTime;
    public static double dblAngle;
    public double dblType;

    // Current Position Array
    private double[] dblCurrentPos = new double[2];

    public void fillVariables(double dblType) {
        if (this.dblType == 1) {
            this.dblAcceleration = -dblGravity;
            this.dblTime = (-2 * dblVel1Y) / dblAcceleration;
            this.dblDistX = dblVel1X * dblTime;
        } else if (this.dblType == 2) {
            this.dblDistY = -this.dblDistY;
            this.dblAcceleration = -dblGravity;
            this.dblTime = Math.sqrt(dblDistY / (dblAcceleration/2));
            this.dblDistX = dblVel1X * dblTime;
        } else if (this.dblType == 3 || this.dblType == 4) {
            this.dblDistY = -this.dblDistY;
            this.dblAcceleration = -dblGravity;
            this.dblTime = (-dblVel1Y-Math.sqrt(Math.pow(dblVel1Y,2) - (4 * (this.dblAcceleration/2) * (-this.dblDistY)))) / (2 * (dblAcceleration/2));
            this.dblDistX = dblVel1X * dblTime;
        } else {
            System.out.println("Error! Incorrect type specified!");
        }
    }
    public double[] getCurrentPos(double dblCurrentTime) {
        this.dblCurrentPos[0] = this.dblVel1X * dblCurrentTime;
        this.dblCurrentPos[1] = -this.dblDistY + (this.dblVel1Y * dblCurrentTime) + ((this.dblAcceleration * Math.pow(dblCurrentTime, 2)) / 2);
        calcStats(dblCurrentPos[0], dblCurrentPos[1], dblCurrentTime);

        return (this.dblCurrentPos);
    }
    public void calcStats(double dblCurrentX, double dblCurrentY, double dblCurrentTime) {
        if (this.dblType == 2 || this.dblType == 4) {
            this.dblDistYMax = -this.dblDistY;
        } else {
            this.dblDistYMax = (Math.pow(this.dblVel1Y, 2) / (2 * this.dblGravity)) + -this.dblDistY;
        }
        this.dblVelY = (this.dblCurrentPos[1] / dblCurrentTime) - (4.9 * dblCurrentTime);
        this.dblVel = Math.sqrt(Math.pow(this.dblVel1X, 2) + Math.pow(this.dblVelY, 2));
        this.dblCurrentAngle = Math.toDegrees(Math.asin((this.dblVelY / this.dblVel)));

        if (this.dblVel > this.dblVel1 && this.dblDistY == 0) {
            this.dblVel = this.dblVel1;
            this.dblVelY = -1 * this.dblVel1Y;
            this.dblCurrentAngle = this.dblAngle;
        }
    }
    // CONSTRUCTOR
    public Ball(double dblV1, double dblAng, double dblHeight) { // Ball constructor requires V1, Angle, & Height
        // Set local variables from constructor variables
        this.dblVel1 = dblV1;
        this.dblVel1X = dblV1 * Math.cos(Math.toRadians(dblAng));
        this.dblVel1Y = dblV1 * Math.sin(Math.toRadians(dblAng));

        this.dblDistY = dblHeight;
        this.dblAngle = dblAng;

        // Determine projectile type
        if (this.dblDistY == 0) {
            this.dblType = 1;
        } else {
            if (this.dblAngle == 0) {
                this.dblType = 2;
            } else if (this.dblAngle > 0 && this.dblAngle <= 90) {
                this.dblType = 3;
            } else if (this.dblAngle < 0 && this.dblAngle >= -90) {
                this.dblType = 4;
            }
        }
        fillVariables(this.dblType);
    }
}
