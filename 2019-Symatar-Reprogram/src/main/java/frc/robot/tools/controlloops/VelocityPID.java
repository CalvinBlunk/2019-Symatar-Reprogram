package frc.robot.tools.controlloops;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.sensors.encoders.*;

public class VelocityPID extends Command {
  private double speed;
  private TalonSRX talon;
  private double f;
  private double p;
  private double i;
  private double d;
  private int profile;
  private boolean finish;
  private DriveEncoder driveEncoder;
  //runs a PIDF look to keep talons at desired velcotities, especially useful in motion profile following algorithms and cascading PID loops
  public VelocityPID(double chosenSpeed, TalonSRX chosenTalon, int profileSlot, double kf, double kp, double ki, double kd) {
    speed = chosenSpeed;
    talon = chosenTalon;
    driveEncoder = new DriveEncoder(talon, talon.getSelectedSensorPosition(0));
    f = kf;
    p = kp;
    i = ki;
    d = kd;
    profile = profileSlot;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    driveEncoder = new DriveEncoder(talon, talon.getSelectedSensorPosition(0));
    talon.selectProfileSlot(profile, 0);
    talon.config_kF(profile, f, 0);
    talon.config_kP(profile, p, 0);
    talon.config_kI(profile, i, 0);
    talon.config_kD(profile, d, 0);
    talon.set(ControlMode.Velocity, Constants.fpsToTicksPer100Ms(speed));

    finish = false;
  }
  public void changeDesiredSpeed(double feetPerSecond){
    speed = feetPerSecond;
    talon.set(ControlMode.Velocity, Constants.fpsToTicksPer100Ms(speed));
  }
  public double getSpeed(){
    return driveEncoder.getVelocity();
  }
  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

 
  public void endPID(){
    finish = true;
  }
  // Make this return true when this Command no longer needs to run execute()
  //TODO DO NOT ALLOW THIS COMMAND TO RUN WITHOUT SOME EXTERNAL END STATE, DESTROY THIS OBJECT WHEN NO LONGER IN USE
  @Override
  protected boolean isFinished() {
    return finish;
  }

  // Called once after isFinished returns true
  
  @Override
  protected void end() {
    talon.configNominalOutputForward(0.0);
    talon.configNominalOutputReverse(0.0);
    talon.set(ControlMode.Velocity, 0);
    talon.set(ControlMode.PercentOutput, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}