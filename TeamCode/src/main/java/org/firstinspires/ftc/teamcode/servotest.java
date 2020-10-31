

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


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

    @TeleOp(name="servotest", group="Linear Opmode")
    @Disabled
    public class servotest extends LinearOpMode {

        // Declare OpMode members.
        private ElapsedTime runtime = new ElapsedTime();
        private Servo leftClawServo;
        private Servo rightClawServo;

        @Override
        public void runOpMode() {

            leftClawServo = hardwareMap.get(Servo.class, "LeftClaw");
            rightClawServo = hardwareMap.get(Servo.class, "RightClaw");
            waitForStart();
            runtime.reset();
            leftClawServo.setPosition(.3);
            rightClawServo.setPosition(.3);
            // run until the end of the match (driver presses STOP)
            while (opModeIsActive()) {

              if (gamepad1.y) {
              leftClawServo.setPosition(.45);
              rightClawServo.setPosition(.45);
              }
              if (gamepad1.x) {
                  leftClawServo.setPosition(0);
                  rightClawServo.setPosition(0);
              }

                telemetry.addData("Status", "Run Time: " + runtime.toString());
                telemetry.addData("Gamepad1 button pressed", gamepad1.getRobocolMsgType());
                telemetry.addData("ServoMotors", "left (%.2f), right (%.2f)", leftClawServo.getPosition(), rightClawServo.getPosition());
                telemetry.update();

            }
        }
    }

