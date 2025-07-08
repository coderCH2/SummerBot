// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class CoralArmSubsystem extends SubsystemBase {
  /** Creates a new CoralArmSubsystem. */
  private SparkMax m_armMotor = new SparkMax(Constants.CoralArmConstants.kArmPort, MotorType.kBrushless);
  private PIDController m_armPID = new PIDController(Constants.CoralArmConstants.kArmP,Constants.CoralArmConstants.kArmI,Constants.CoralArmConstants.kArmD);
  private double m_setpoint = 0.0;

  public CoralArmSubsystem() {
    m_armMotor.getEncoder().setPosition(0);
    SparkMaxConfig config = new SparkMaxConfig();
    config.smartCurrentLimit(40);
    config.idleMode(IdleMode.kBrake);//change back to brake
    config.inverted(false);
    m_armMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void periodic() {
    double encoderVal = m_armMotor.getEncoder().getPosition();
    SmartDashboard.putNumber("ArmEncoder",encoderVal); //get encoder val and display

    double pidOutput = m_armPID.calculate(encoderVal,m_setpoint);
    SmartDashboard.putNumber("PID Output",pidOutput);
    m_armMotor.set(pidOutput); //get PID val and display and set

    // This method will be called once per scheduler run
  }

  public void setDesiredAngle (double input){
    m_setpoint = input;
  }


}
