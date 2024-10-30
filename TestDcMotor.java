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
    final double LIFT_TICKS_PER_MM = (111132.0 / 289.0) / 120.0;
    final double LIFT_COLLAPSED = 0;
    final double LIFT_SCORING_IN_HIGH_BASKET = 480 * LIFT_TICKS_PER_MM;

    private double liftPosition = LIFT_COLLAPSED;

    private double looptime, cycletime, oldtime;

    private double current, target;
    @Override
    public void init() {
        this.liftMotor = hardwareMap.dcMotor.get("liftMotor");
        liftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        liftMotor.setTargetPosition(0);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void loop() {

        looptime = this.getRuntime();
        cycletime = looptime-oldtime;
        oldtime = looptime;

        if (gamepad2.dpad_up) {
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

    private double liftPositionSet(double position){
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
