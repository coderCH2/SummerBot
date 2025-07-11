// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.estimator.MecanumDrivePoseEstimator;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {
  private SparkMax m_frontLeftMotor = new SparkMax(DriveConstants.kFrontLeftPort, MotorType.kBrushless);
  private SparkMax m_frontRightMotor = new SparkMax(DriveConstants.kFrontRightPort, MotorType.kBrushless);
  private SparkMax m_backLeftMotor = new SparkMax(DriveConstants.kBackLeftPort, MotorType.kBrushless);
  private SparkMax m_backRightMotor = new SparkMax(DriveConstants.kBackRightPort, MotorType.kBrushless);
  private MecanumDrive m_mecanumDrive = new MecanumDrive(m_frontLeftMotor, m_backLeftMotor, m_frontRightMotor, m_backRightMotor);

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {
    SparkMaxConfig frontLeftConfig = new SparkMaxConfig();
    frontLeftConfig.smartCurrentLimit(40);
    frontLeftConfig.inverted(false);
    frontLeftConfig.idleMode(IdleMode.kBrake);
    m_frontLeftMotor.configure(frontLeftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    
    SparkMaxConfig frontRightConfig = new SparkMaxConfig();
    frontRightConfig.smartCurrentLimit(40);
    frontRightConfig.inverted(true);
    frontRightConfig.idleMode(IdleMode.kBrake);
    m_frontRightMotor.configure(frontRightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    SparkMaxConfig backLeftConfig = new SparkMaxConfig();
    backLeftConfig.smartCurrentLimit(40);
    backLeftConfig.inverted(false);
    backLeftConfig.idleMode(IdleMode.kBrake);
    m_backLeftMotor.configure(backLeftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    SparkMaxConfig backRightConfig = new SparkMaxConfig();
    backRightConfig.smartCurrentLimit(40);
    backRightConfig.inverted(true);
    backRightConfig.idleMode(IdleMode.kBrake);
    m_backRightMotor.configure(backRightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public Command driveCommand(DoubleSupplier x, DoubleSupplier y, DoubleSupplier theta) {
    return new RunCommand(() -> {
      m_mecanumDrive.driveCartesian(x.getAsDouble(), y.getAsDouble(), theta.getAsDouble());
    }, this);
  }
}
