// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.revrobotics.Rev2mDistanceSensor;
import com.revrobotics.Rev2mDistanceSensor.Port;
import com.revrobotics.Rev2mDistanceSensor.RangeProfile;
import com.revrobotics.Rev2mDistanceSensor.Unit;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CoralRollerConstants;

public class CoralRollerSubsystem extends SubsystemBase {
  private SparkMax m_rollerMotor = new SparkMax(CoralRollerConstants.kRollerPort, MotorType.kBrushless);
//  private Rev2mDistanceSensor m_sensor = new Rev2mDistanceSensor(Port.kMXP, Unit.kMillimeters, RangeProfile.kHighSpeed);
  private double m_setpoint = 0.0;

  /** Creates a new CoralRollerSubsystem. */
  public CoralRollerSubsystem() {
    SparkMaxConfig config = new SparkMaxConfig();
    config.smartCurrentLimit(40);
    config.idleMode(IdleMode.kBrake);
    config.inverted(false);
    m_rollerMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void periodic() {
    m_rollerMotor.set(m_setpoint);
  }

  public void setDesiredSpeed(double speed) {
    m_setpoint = speed;
  }
}
