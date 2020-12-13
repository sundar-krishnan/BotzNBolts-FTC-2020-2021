package org.firstinspires.ftc.teamcode.BnB2021;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

package org.firstinspires.ftc.teamcode.BnB2021;

        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.DcMotorSimple;
        import com.qualcomm.robotcore.hardware.Servo;
        import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="BnB_OpMode_Pranav", group="Linear Opmode")
//@Disabled
public class BnB_OpMode_Manual extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotor throwerDrive = null;
    private DcMotor ringrollerDrive = null;
    private Servo grabberServo = null;
    private Servo collectorServo = null;
    //    private Servo topClawServo = null;
//    private Servo leftClawWristServo = null;
//    private Servo rightClawWristServo = null;
    private DcMotor armLifter = null;


    private double servoPosition = 0.0;
    static final double INCREMENT = 0.1;     // amount to slew servo each CYCLE_MS cycle
    static final double INCREMENTWRIST = 0.1;
    static final int    CYCLE_MS  =  100;     // period of each cycle
    static final double MAX_POS   =  1.0;     // Maximum rotational position
    static final double MIN_POS   =  0.0;     // Minimum rotational position
    static final double MAX_POS_WRIST   =  0.3;     // Maximum rotational position
    static final double MIN_POS_WRIST    =  0.8;     // Minimum rotational position
    double speedAdjust =7.0;
    double  position = 0.0; //(MAX_POS - MIN_POS) / 2; // Start at halfway position
    double  positionWrist = 5.0;
    double armMotorPower=1.0;
    int targetPosition = 0;
    double drivePower = 0.5;

    @Override
    public void runOpMode()
    {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        initializeDriveMotor();
        initializeServoMotor();
        initializeArmMotor();
        initializeThrowerMotor();
//        initializeServoMotorWrist();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        //runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

//            driveBot();
            driveBot2();
            driveClaws();
            driveArm();
//            driveClawWrist();

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Drive Motor Front","left (%.2f), right (%.2f)", leftFrontDrive.getPower(), rightFrontDrive.getPower());
            telemetry.addData("Drive Motor Back", "left (%.2f), right (%.2f)", leftBackDrive.getPower(), rightBackDrive.getPower());
            telemetry.addData("speedAdjust","speed (%.2f)",  speedAdjust);
            telemetry.addData("Gamepad1 button pressed", gamepad1.getRobocolMsgType());
            telemetry.addData("ServoMotors", "left (%.2f), right (%.2f)", grabberServo.getPosition(), collectorServo.getPosition());
            telemetry.addData("ArmTargetPosition", "ArmTargetPosition: " + armLifter.getTargetPosition());
            telemetry.addData("armLifterDirection", "ArmLifterDirection: " + armLifter.getDirection());
//            telemetry.addData("topClawServo", "topClawServo: " + topClawServo.getPosition() + " Direction " + topClawServo.getDirection());
//            telemetry.addData("RightWristClawServo", "RightWristClawServo: " + rightClawWristServo.getPosition());
//            telemetry.addData("LeftWristClawServo", "LeftWristClawServo: " + leftClawWristServo.getPosition());
            telemetry.update();
        }
    }

    private void driveBot2()
    {
        if (gamepad1.right_stick_x > 0.0)
        {
            leftBackDrive.setPower(drivePower);
            rightBackDrive.setPower(-drivePower);
            leftFrontDrive.setPower(-drivePower);
            rightFrontDrive.setPower(drivePower);
        }
        else if(gamepad1.right_stick_x < 0.0)
        {
            leftBackDrive.setPower(-drivePower);
            rightBackDrive.setPower(drivePower);
            leftFrontDrive.setPower(drivePower);
            rightFrontDrive.setPower(-drivePower);

        }
        else if(gamepad1.left_stick_x < 0.0)
        {
            leftBackDrive.setPower(drivePower);
            rightBackDrive.setPower(-drivePower);
            leftFrontDrive.setPower(drivePower);
            rightFrontDrive.setPower(-drivePower);

        }
        else if(gamepad1.left_stick_x > 0.0)
        {
            leftBackDrive.setPower(-drivePower);
            rightBackDrive.setPower(drivePower);
            leftFrontDrive.setPower(-drivePower);
            rightFrontDrive.setPower(drivePower);

        }
//        else if(gamepad1.right_stick_y != 0)
//        {
//            leftBackDrive.setPower(drivePower);
//            rightBackDrive.setPower(drivePower);
//            leftFrontDrive.setPower(drivePower);
//            rightFrontDrive.setPower(drivePower);
//        }
//        else if(gamepad1.right_stick_y == 0)
        else
        {
            leftBackDrive.setPower(gamepad1.right_stick_y/2.0);
            rightBackDrive.setPower(gamepad1.right_stick_y/2.0);
            leftFrontDrive.setPower(gamepad1.right_stick_y/2.0);
            rightFrontDrive.setPower(gamepad1.right_stick_y/2.0);
        }
//        leftBackDrive.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x));
//        rightBackDrive.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x));
//        leftFrontDrive.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x));
//        rightFrontDrive.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x));
    }

    private void initializeArmMotor()
    {
        armLifter = hardwareMap.get(DcMotor.class, "ArmLifter");
        armLifter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        targetPosition = armLifter.getTargetPosition();
        armLifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armLifter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armLifter.setTargetPosition(0);
        armLifter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //armLifter.setDirection(DcMotor.Direction.FORWARD);
        //armLifter.setTargetPosition(0);
        //armLifter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armLifter.setPower(1.0);
    }

    private void driveArm()
    {
        if ( gamepad1.dpad_down  )
        {
            if (targetPosition < 0) {targetPosition = 0;}
            targetPosition += 2;
            armMotorPower = 1.0;
        }
        else if (gamepad1.dpad_up )
        {
            if (targetPosition > 0) {targetPosition = 0;}
            targetPosition -= 2;
            armMotorPower = -1.0;
        }
        else
        {
            targetPosition = 0;
            armMotorPower = 0.0;
        }
//        while (opModeIsActive() && armLifter.isBusy())
//        {
//            telemetry.addData("encoder",armLifter.getCurrentPosition() + "busy= " + armLifter.isBusy());
//            telemetry.update();
//            idle();
//            sleep(1000);
//        }
        armLifter.setTargetPosition(targetPosition);
        armLifter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armLifter.setPower(armMotorPower);
        targetPosition = armLifter.getTargetPosition();


//        armLifter.setTargetPosition(0);
//        armLifter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        armLifter.setPower(0);
//        armLifter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private void initializeDriveMotor()
    {
        leftFrontDrive = hardwareMap.get(DcMotor.class, "LeftFrontDrive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "RightFrontDrive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "LeftBackDrive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "RightBackDrive");

        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    private void initializeThrowerMotor()
    {
        throwerDrive = hardwareMap.get(DcMotor.class, "Thrower");
        ringrollerDrive = hardwareMap.get(DcMotor.class, "RingRoller");

        throwerDrive.setDirection(DcMotor.Direction.FORWARD);
        ringrollerDrive.setDirection(DcMotor.Direction.REVERSE);

        throwerDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ringrollerDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        throwerDrive.setPower(100.0);
        ringrollerDrive.setPower(1.0);
    }

    private void initializeServoMotor()
    {
        grabberServo = hardwareMap.get(Servo.class, "Grabber");
        collectorServo = hardwareMap.get(Servo.class, "Collector");
//        topClawServo = hardwareMap.get(Servo.class, "TopClaw");
        grabberServo.setDirection(Servo.Direction.FORWARD);
        collectorServo.setDirection(Servo.Direction.FORWARD);
//        topClawServo.setDirection(Servo.Direction.FORWARD);
        grabberServo.setPosition(position);
        collectorServo.setPosition(position);
//        topClawServo.setPosition(position);
    }

//    private void initializeServoMotorWrist()
//    {
//        leftClawWristServo = hardwareMap.get(Servo.class, "LeftClawWrist");
//        leftClawWristServo.setDirection(Servo.Direction.FORWARD);
//        positionWrist = leftClawWristServo.getPosition();
//        leftClawWristServo.setPosition(1.0);
//
//        rightClawWristServo = hardwareMap.get(Servo.class, "RightClawWrist");
//        rightClawWristServo.setDirection(Servo.Direction.FORWARD);
//        positionWrist = rightClawWristServo.getPosition();
//        rightClawWristServo.setPosition(0.0);
//    }

//    private void driveBot()
//    {
//        if(gamepad1.dpad_left ==true)
//        {
//            speedAdjust -=1;
//        }
//        if(gamepad1.dpad_right ==true)
//        {
//            speedAdjust +=1;
//        }
//
//        leftBackDrive.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x)*(-speedAdjust/10));
//        rightBackDrive.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x)*(-speedAdjust/10));
//        leftFrontDrive.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x)*(-speedAdjust/10));
//        rightFrontDrive.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x)*(-speedAdjust/10));
//    }

    private void  driveClaws()
    {
        // slew the servo, according to the rampUp (direction) variable.
        if (position!=MAX_POS && gamepad1.y)
        {
            // Keep stepping up until we hit the max value.
            position += INCREMENT ;
            if (position >= MAX_POS )
            {
                position = MAX_POS;
            }
        }
        else if (position != MIN_POS && gamepad1.a)
        {
            // Keep stepping down until we hit the min value.
            position -= INCREMENT ;
            if (position <= MIN_POS )
            {
                position = MIN_POS;

            }
        }
        // Set the servo to the new position and pause;
        grabberServo.setPosition(position);
        collectorServo.setPosition(MAX_POS);
//        topClawServo.setPosition(position);
    }

//    private void  driveClawWrist()
//    {
//        // slew the servo, according to the rampUp (direction) variable.
//        if ( gamepad1.right_bumper)
//        {
//            // Set the servo to the new position and pause;
//            leftClawWristServo.setPosition(0.6);
//            rightClawWristServo.setPosition(0.3);
//        }
//        else if (gamepad1.left_bumper)
//        {
//            // Set the servo to the new position and pause;
//            leftClawWristServo.setPosition(1.0);
//            rightClawWristServo.setPosition(0.0);
//        }
//
//    }
}

