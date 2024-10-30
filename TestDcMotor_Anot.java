package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;


@TeleOp(name = "DcMotorHeating")
public class TestDcMotor extends OpMode {
    private DcMotor liftMotor;
    final double LIFT_TICKS_PER_MM = (111132.0 / 289.0) / 120.0;                    // Calculated Travel Per MM
    final double LIFT_COLLAPSED = 0;                                                // Start Position
    final double LIFT_SCORING_IN_HIGH_BASKET = 480 * LIFT_TICKS_PER_MM;             // Setting High Basket Height

    private double liftPosition = LIFT_COLLAPSED;                                   // Call back to lift position

    private double looptime, cycletime, oldtime;

    private double current, target;
    @Override
    public void init() {                                                        // - (Class) Init
        this.liftMotor = hardwareMap.dcMotor.get("liftMotor");
        liftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        liftMotor.setTargetPosition(0);                           // <-- Make this the last call after reset encoder
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);       // <-- Probably reverse these two calls
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);// <--/
      //liftMotor.setPower(0.5);                                  Ex: Sets the power for the motors movement this can be better employed through PID or just a PD function
    }

    @Override
    public void loop() {                                                        // - (Class) Run

        looptime = this.getRuntime();
        cycletime = looptime-oldtime;
        oldtime = looptime;

        if (gamepad2.dpad_up) {                                                     // Calling (Movement of Lift)
            liftPosition = liftPositionSet(liftPosition + 2800 * cycletime);


        } else if (gamepad2.dpad_down) {
            liftPosition = liftPositionSet(liftPosition - 2800 * cycletime);


        }
        telemetry.addData("",""+System.currentTimeMillis());
        telemetry.addData("Current Pos",liftMotor.getCurrentPosition()+"~"+current);
        telemetry.addData("Target Position",liftMotor.getTargetPosition()+"~"+target);
        current  = liftMotor.getCurrentPosition();
        target  = liftMotor.getTargetPosition();
        telemetry.addData("Power",liftMotor.getPower());
        telemetry.addData("lift variable", String.valueOf(liftPosition));
        telemetry.addData("liftMotor Current:", String.valueOf(((DcMotorEx) liftMotor).getCurrent(CurrentUnit.AMPS)));
        telemetry.update();


    }

    private double liftPositionSet(double position){                            // - (Class) Movement of Lift <Setting Lift Position>
        if (position > LIFT_SCORING_IN_HIGH_BASKET) {
            position = LIFT_SCORING_IN_HIGH_BASKET;
        }
        if (position < 0) {
            position = 0;
        }
        liftMotor.setTargetPosition((int) position);
        ((DcMotorEx) liftMotor).setVelocity(2100);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        return position;
    }



}
