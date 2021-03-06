USE [CarRental]
GO
ALTER TABLE [dbo].[OrderDetail] DROP CONSTRAINT [FK_OrderDetail_Order]
GO
ALTER TABLE [dbo].[OrderDetail] DROP CONSTRAINT [FK_OrderDetail_Car]
GO
ALTER TABLE [dbo].[Order] DROP CONSTRAINT [FK_Order_User]
GO
ALTER TABLE [dbo].[Order] DROP CONSTRAINT [FK_Order_Discount]
GO
ALTER TABLE [dbo].[Feedback] DROP CONSTRAINT [FK_Feedback_Order]
GO
/****** Object:  Table [dbo].[User]    Script Date: 3/4/2021 7:23:22 PM ******/
DROP TABLE [dbo].[User]
GO
/****** Object:  Table [dbo].[OrderDetail]    Script Date: 3/4/2021 7:23:22 PM ******/
DROP TABLE [dbo].[OrderDetail]
GO
/****** Object:  Table [dbo].[Order]    Script Date: 3/4/2021 7:23:22 PM ******/
DROP TABLE [dbo].[Order]
GO
/****** Object:  Table [dbo].[Feedback]    Script Date: 3/4/2021 7:23:22 PM ******/
DROP TABLE [dbo].[Feedback]
GO
/****** Object:  Table [dbo].[Discount]    Script Date: 3/4/2021 7:23:22 PM ******/
DROP TABLE [dbo].[Discount]
GO
/****** Object:  Table [dbo].[Car]    Script Date: 3/4/2021 7:23:22 PM ******/
DROP TABLE [dbo].[Car]
GO
USE [master]
GO
/****** Object:  Database [CarRental]    Script Date: 3/4/2021 7:23:22 PM ******/
DROP DATABASE [CarRental]
GO
/****** Object:  Database [CarRental]    Script Date: 3/4/2021 7:23:22 PM ******/
CREATE DATABASE [CarRental]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'CarRental', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\CarRental.mdf' , SIZE = 4096KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'CarRental_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\CarRental_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [CarRental] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [CarRental].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [CarRental] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [CarRental] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [CarRental] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [CarRental] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [CarRental] SET ARITHABORT OFF 
GO
ALTER DATABASE [CarRental] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [CarRental] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [CarRental] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [CarRental] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [CarRental] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [CarRental] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [CarRental] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [CarRental] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [CarRental] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [CarRental] SET  DISABLE_BROKER 
GO
ALTER DATABASE [CarRental] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [CarRental] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [CarRental] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [CarRental] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [CarRental] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [CarRental] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [CarRental] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [CarRental] SET RECOVERY FULL 
GO
ALTER DATABASE [CarRental] SET  MULTI_USER 
GO
ALTER DATABASE [CarRental] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [CarRental] SET DB_CHAINING OFF 
GO
ALTER DATABASE [CarRental] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [CarRental] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [CarRental] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'CarRental', N'ON'
GO
USE [CarRental]
GO
/****** Object:  Table [dbo].[Car]    Script Date: 3/4/2021 7:23:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Car](
	[carID] [varchar](50) NOT NULL,
	[carName] [nvarchar](256) NOT NULL,
	[seats] [int] NOT NULL,
	[transmission] [varchar](10) NOT NULL,
	[color] [nvarchar](128) NOT NULL,
	[year] [varchar](4) NOT NULL,
	[category] [nvarchar](128) NOT NULL,
	[price] [float] NOT NULL,
	[quantity] [int] NOT NULL,
	[createDate] [date] NOT NULL,
 CONSTRAINT [PK_Car] PRIMARY KEY CLUSTERED 
(
	[carID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Discount]    Script Date: 3/4/2021 7:23:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Discount](
	[discountCode] [varchar](20) NOT NULL,
	[discountName] [nvarchar](50) NOT NULL,
	[saleOff] [float] NOT NULL,
	[expiryDate] [date] NOT NULL,
 CONSTRAINT [PK_Discount] PRIMARY KEY CLUSTERED 
(
	[discountCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Feedback]    Script Date: 3/4/2021 7:23:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Feedback](
	[feedbackID] [varchar](50) NOT NULL,
	[orderID] [varchar](50) NOT NULL,
	[content] [nvarchar](256) NULL,
	[rating] [int] NOT NULL,
 CONSTRAINT [PK_Feedback] PRIMARY KEY CLUSTERED 
(
	[feedbackID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Order]    Script Date: 3/4/2021 7:23:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Order](
	[orderID] [varchar](50) NOT NULL,
	[email] [varchar](128) NOT NULL,
	[totalPrice] [float] NOT NULL,
	[status] [varchar](10) NOT NULL,
	[discountCode] [varchar](20) NULL,
	[bookingDate] [date] NOT NULL,
 CONSTRAINT [PK_Order] PRIMARY KEY CLUSTERED 
(
	[orderID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[OrderDetail]    Script Date: 3/4/2021 7:23:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[OrderDetail](
	[orderDetailID] [varchar](50) NOT NULL,
	[orderID] [varchar](50) NOT NULL,
	[carID] [varchar](50) NOT NULL,
	[amount] [int] NOT NULL,
	[price] [float] NOT NULL,
	[total] [float] NOT NULL,
	[rentalDate] [date] NOT NULL,
	[returnDate] [date] NOT NULL,
 CONSTRAINT [PK_OrderDetail] PRIMARY KEY CLUSTERED 
(
	[orderDetailID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[User]    Script Date: 3/4/2021 7:23:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[User](
	[email] [varchar](128) NOT NULL,
	[password] [varchar](30) NOT NULL,
	[fullname] [nvarchar](256) NOT NULL,
	[phone] [varchar](12) NOT NULL,
	[address] [nvarchar](256) NOT NULL,
	[createDate] [date] NOT NULL,
	[status] [varchar](10) NOT NULL,
	[role] [varchar](20) NOT NULL,
 CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0001', N'Toyota Vios', 5, N'AT', N'Black', N'2018', N'Sedan', 20, 1, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0002', N'Toyota Innova', 7, N'AT', N'Grey', N'2018', N'MPV', 21, 2, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0003', N'Toyota Fortuner', 7, N'AT', N'White', N'2018', N'SUV', 22, 3, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0004', N'Kia Morning', 5, N'AT', N'Red', N'2018', N'Hatchback', 10, 2, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0005', N'Kia Sorento', 7, N'MT', N'Black', N'2018', N'SUV', 18, 5, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0006', N'Mitsubishi Attrage', 5, N'MT', N'Grey', N'2018', N'Sedan', 11, 2, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0007', N'Mitsubishi Xpander', 7, N'AT', N'White', N'2018', N'SUV', 15, 3, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0008', N'Kia Soluto', 5, N'AT', N'White', N'2018', N'Sedan', 14, 4, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0009', N'Vinfast Fadil', 5, N'AT', N'Brown', N'2018', N'Hatchback', 9, 5, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0010', N'Huyndai Accent', 5, N'AT', N'Black', N'2018', N'Sedan', 19, 2, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0011', N'Huyndai Grand I10', 5, N'MT', N'White', N'2018', N'Hatchback', 12, 6, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0012', N'Honda City', 5, N'AT', N'Black', N'2018', N'Sedan', 17, 3, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0013', N'Ford Ecosport', 5, N'AT', N'Yellow', N'2018', N'SUV', 16, 2, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0014', N'Honda Civic', 5, N'AT', N'Red', N'2018', N'Sedan', 21, 5, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0015', N'Mazda 2', 5, N'AT', N'Red', N'2018', N'Sedan', 8, 3, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0016', N'Chevrolet Spark', 5, N'MT', N'Red', N'2018', N'Hatchback', 7, 7, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0017', N'Chevrolet Avero', 5, N'AT', N'Grey', N'2018', N'Sedan', 13, 6, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0018', N'BMW 320i', 5, N'AT', N'Blue', N'2019', N'Sedan', 30, 2, CAST(N'2021-03-03' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0019', N'BMW X7', 7, N'AT', N'Black', N'2019', N'SUV', 31, 1, CAST(N'2021-03-03' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0020', N'Ford Ranger', 5, N'AT', N'Blue', N'2019', N'Truck', 32, 3, CAST(N'2021-03-03' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0021', N'Mitsubishi Triton', 5, N'AT', N'Orange', N'2018', N'Truck', 31.5, 2, CAST(N'2021-03-03' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0022', N'Toyota Hilux', 5, N'MT', N'White', N'2018', N'Truck', 30.5, 2, CAST(N'2021-03-03' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0023', N'Subaru Forester', 7, N'AT', N'White', N'2018', N'SUV', 33, 1, CAST(N'2021-03-03' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0024', N'Toyota Rush​', 7, N'AT', N'Silver', N'2018', N'SUV', 23, 2, CAST(N'2021-04-03' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0025', N'Toyota Vios', 5, N'AT', N'Black', N'2020', N'Sedan', 21, 1, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0026', N'Toyota Innova', 7, N'AT', N'Grey', N'2020', N'MPV', 22, 2, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0027', N'Toyota Fortuner', 7, N'AT', N'White', N'2020', N'SUV', 23, 3, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0034', N'Kia Morning', 5, N'AT', N'Red', N'2020', N'Hatchback', 11, 2, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0035', N'Kia Sorento', 7, N'MT', N'Black', N'2020', N'SUV', 19, 5, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0036', N'Mitsubishi Attrage', 5, N'MT', N'Grey', N'2020', N'Sedan', 12, 2, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0037', N'Mitsubishi Xpander', 7, N'AT', N'White', N'2020', N'SUV', 16, 3, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0038', N'Kia Soluto', 5, N'AT', N'White', N'2020', N'Sedan', 15, 4, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0039', N'Vinfast Fadil', 5, N'AT', N'Brown', N'2020', N'Hatchback', 10, 5, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0040', N'Huyndai Accent', 5, N'AT', N'Black', N'2020', N'Sedan', 20, 2, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0041', N'Huyndai Grand I10', 5, N'MT', N'White', N'2020', N'Hatchback', 13, 6, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0042', N'Honda City', 5, N'AT', N'Black', N'2020', N'Sedan', 18, 3, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0043', N'Ford Ecosport', 5, N'AT', N'Yellow', N'2020', N'SUV', 17, 2, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0044', N'Honda Civic', 5, N'AT', N'Red', N'2020', N'Sedan', 22, 5, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0045', N'Mazda 2', 5, N'AT', N'Red', N'2020', N'Sedan', 9, 3, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0046', N'Chevrolet Spark', 5, N'MT', N'Red', N'2020', N'Hatchback', 8, 7, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0047', N'Chevrolet Avero', 5, N'AT', N'Grey', N'2020', N'Sedan', 14, 6, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0048', N'BMW 320i', 5, N'AT', N'Blue', N'2020', N'Sedan', 31, 2, CAST(N'2021-03-03' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0049', N'BMW X7', 7, N'AT', N'Black', N'2020', N'SUV', 32, 1, CAST(N'2021-03-03' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0050', N'Ford Ranger', 5, N'AT', N'Blue', N'2020', N'Truck', 33, 3, CAST(N'2021-03-03' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0051', N'Mitsubishi Triton', 5, N'AT', N'Orange', N'2020', N'Truck', 32.5, 2, CAST(N'2021-03-03' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0052', N'Toyota Hilux', 5, N'MT', N'White', N'2020', N'Truck', 31.5, 2, CAST(N'2021-03-03' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0053', N'Subaru Forester', 7, N'AT', N'White', N'2020', N'SUV', 34, 1, CAST(N'2021-03-03' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0054', N'Toyota Rush​', 7, N'AT', N'Silver', N'2020', N'SUV', 24, 2, CAST(N'2021-04-03' AS Date))
INSERT [dbo].[Car] ([carID], [carName], [seats], [transmission], [color], [year], [category], [price], [quantity], [createDate]) VALUES (N'0055', N'Toyota Vios', 5, N'AT', N'Black', N'2020', N'Sedan', 21, 1, CAST(N'2021-02-25' AS Date))
INSERT [dbo].[Discount] ([discountCode], [discountName], [saleOff], [expiryDate]) VALUES (N'1', N'Discount 20%', 0.2, CAST(N'2022-01-01' AS Date))
INSERT [dbo].[Discount] ([discountCode], [discountName], [saleOff], [expiryDate]) VALUES (N'2', N'Discount 50%', 0.5, CAST(N'2020-01-01' AS Date))
INSERT [dbo].[Feedback] ([feedbackID], [orderID], [content], [rating]) VALUES (N'5f58781d-d6ea-4d55-8204-69c24e45bf17', N'a2f73691-1878-4c67-bb71-3639897626b1', N'Good! Nice', 9)
INSERT [dbo].[Order] ([orderID], [email], [totalPrice], [status], [discountCode], [bookingDate]) VALUES (N'1f3f35b5-64bb-45d4-a832-fb5038e04b4f', N'test@gmail.com', 96, N'Pending', NULL, CAST(N'2021-03-02' AS Date))
INSERT [dbo].[Order] ([orderID], [email], [totalPrice], [status], [discountCode], [bookingDate]) VALUES (N'40526f16-47b6-4e54-ad2d-6661527fc56e', N'test@gmail.com', 30, N'Renting', NULL, CAST(N'2021-03-01' AS Date))
INSERT [dbo].[Order] ([orderID], [email], [totalPrice], [status], [discountCode], [bookingDate]) VALUES (N'444183c8-fe34-4840-b745-d5d445d6dcef', N'test@gmail.com', 230, N'Cancelled', NULL, CAST(N'2021-03-03' AS Date))
INSERT [dbo].[Order] ([orderID], [email], [totalPrice], [status], [discountCode], [bookingDate]) VALUES (N'5c12a15e-0008-4f23-ae9d-8d89228a8ade', N'benny20001456@gmail.com', 24, N'Pending', N'1', CAST(N'2021-03-03' AS Date))
INSERT [dbo].[Order] ([orderID], [email], [totalPrice], [status], [discountCode], [bookingDate]) VALUES (N'7410f0c7-78a4-4f26-b897-5eb2da6ffc2b', N'test@gmail.com', 319.20001220703125, N'Pending', N'1', CAST(N'2021-03-03' AS Date))
INSERT [dbo].[Order] ([orderID], [email], [totalPrice], [status], [discountCode], [bookingDate]) VALUES (N'89001889-3cbb-437b-9b54-6a0d764c9a42', N'test@gmail.com', 396, N'Renting', N'1', CAST(N'2021-03-04' AS Date))
INSERT [dbo].[Order] ([orderID], [email], [totalPrice], [status], [discountCode], [bookingDate]) VALUES (N'89743b60-09a0-4cda-96ba-9ce8412eb551', N'test@gmail.com', 230, N'Cancelled', NULL, CAST(N'2021-03-03' AS Date))
INSERT [dbo].[Order] ([orderID], [email], [totalPrice], [status], [discountCode], [bookingDate]) VALUES (N'a2f73691-1878-4c67-bb71-3639897626b1', N'test@gmail.com', 66.4000015258789, N'Finish', N'1', CAST(N'2021-03-01' AS Date))
INSERT [dbo].[OrderDetail] ([orderDetailID], [orderID], [carID], [amount], [price], [total], [rentalDate], [returnDate]) VALUES (N'39ec16ea-af6a-497f-bd8d-5cfb21391f66', N'a2f73691-1878-4c67-bb71-3639897626b1', N'0003', 1, 22, 22, CAST(N'2021-03-03' AS Date), CAST(N'2021-03-04' AS Date))
INSERT [dbo].[OrderDetail] ([orderDetailID], [orderID], [carID], [amount], [price], [total], [rentalDate], [returnDate]) VALUES (N'40653bde-1d45-4394-bab7-0927bea75ee5', N'40526f16-47b6-4e54-ad2d-6661527fc56e', N'0004', 1, 10, 10, CAST(N'2021-03-05' AS Date), CAST(N'2021-03-06' AS Date))
INSERT [dbo].[OrderDetail] ([orderDetailID], [orderID], [carID], [amount], [price], [total], [rentalDate], [returnDate]) VALUES (N'4e99f0a3-fa86-497b-9ba4-175629fa19ea', N'89743b60-09a0-4cda-96ba-9ce8412eb551', N'0024', 1, 23, 230, CAST(N'2021-03-04' AS Date), CAST(N'2021-03-14' AS Date))
INSERT [dbo].[OrderDetail] ([orderDetailID], [orderID], [carID], [amount], [price], [total], [rentalDate], [returnDate]) VALUES (N'b6348275-49a7-4aa7-ad04-f9bad9e12fb3', N'89001889-3cbb-437b-9b54-6a0d764c9a42', N'0050', 3, 33, 495, CAST(N'2021-03-05' AS Date), CAST(N'2021-03-10' AS Date))
INSERT [dbo].[OrderDetail] ([orderDetailID], [orderID], [carID], [amount], [price], [total], [rentalDate], [returnDate]) VALUES (N'bb04c65e-6778-40cc-aa04-c306eaafb571', N'40526f16-47b6-4e54-ad2d-6661527fc56e', N'0001', 1, 20, 20, CAST(N'2021-03-03' AS Date), CAST(N'2021-03-04' AS Date))
INSERT [dbo].[OrderDetail] ([orderDetailID], [orderID], [carID], [amount], [price], [total], [rentalDate], [returnDate]) VALUES (N'becae3e1-3f3f-4842-9b94-000ab75ae074', N'1f3f35b5-64bb-45d4-a832-fb5038e04b4f', N'0017', 2, 13, 52, CAST(N'2021-03-04' AS Date), CAST(N'2021-03-06' AS Date))
INSERT [dbo].[OrderDetail] ([orderDetailID], [orderID], [carID], [amount], [price], [total], [rentalDate], [returnDate]) VALUES (N'c660fac2-33df-4349-936c-61991cc5effd', N'444183c8-fe34-4840-b745-d5d445d6dcef', N'0024', 1, 23, 230, CAST(N'2021-03-04' AS Date), CAST(N'2021-03-14' AS Date))
INSERT [dbo].[OrderDetail] ([orderDetailID], [orderID], [carID], [amount], [price], [total], [rentalDate], [returnDate]) VALUES (N'cfdb9a10-fcad-4eb5-9ec5-4c8cae0bdac3', N'a2f73691-1878-4c67-bb71-3639897626b1', N'0001', 2, 20, 40, CAST(N'2021-03-03' AS Date), CAST(N'2021-03-04' AS Date))
INSERT [dbo].[OrderDetail] ([orderDetailID], [orderID], [carID], [amount], [price], [total], [rentalDate], [returnDate]) VALUES (N'd46a18d8-88cc-4b32-a98b-4417401a9ee1', N'5c12a15e-0008-4f23-ae9d-8d89228a8ade', N'0018', 1, 30, 30, CAST(N'2021-03-04' AS Date), CAST(N'2021-03-05' AS Date))
INSERT [dbo].[OrderDetail] ([orderDetailID], [orderID], [carID], [amount], [price], [total], [rentalDate], [returnDate]) VALUES (N'dd851dde-aae0-467b-b7be-80b563ec7adb', N'a2f73691-1878-4c67-bb71-3639897626b1', N'0002', 1, 21, 21, CAST(N'2021-03-03' AS Date), CAST(N'2021-03-04' AS Date))
INSERT [dbo].[OrderDetail] ([orderDetailID], [orderID], [carID], [amount], [price], [total], [rentalDate], [returnDate]) VALUES (N'e31a0e4a-e71a-4fa7-909c-ed2fddd4ee2d', N'7410f0c7-78a4-4f26-b897-5eb2da6ffc2b', N'0035', 3, 19, 399, CAST(N'2021-03-07' AS Date), CAST(N'2021-03-14' AS Date))
INSERT [dbo].[OrderDetail] ([orderDetailID], [orderID], [carID], [amount], [price], [total], [rentalDate], [returnDate]) VALUES (N'fb63ad51-aec0-4a04-8ead-1998da22baa0', N'1f3f35b5-64bb-45d4-a832-fb5038e04b4f', N'0006', 2, 11, 44, CAST(N'2021-03-04' AS Date), CAST(N'2021-03-06' AS Date))
INSERT [dbo].[User] ([email], [password], [fullname], [phone], [address], [createDate], [status], [role]) VALUES (N'admin@gmail.com', N'123456', N'Admin la tao', N'0123456789', N'Thien Dang', CAST(N'2021-02-26' AS Date), N'active', N'admin')
INSERT [dbo].[User] ([email], [password], [fullname], [phone], [address], [createDate], [status], [role]) VALUES (N'benny20001456@gmail.com', N'12345678', N'Bach Nguyen', N'0123654987', N'Binh Thanh', CAST(N'2021-02-27' AS Date), N'active', N'cust')
INSERT [dbo].[User] ([email], [password], [fullname], [phone], [address], [createDate], [status], [role]) VALUES (N'test@gmail.com', N'123456', N'Tester la Tao', N'0123456798', N'Thai Binh Duong', CAST(N'2021-02-25' AS Date), N'active', N'cust')
INSERT [dbo].[User] ([email], [password], [fullname], [phone], [address], [createDate], [status], [role]) VALUES (N'test01@gmail.com', N'123456', N'Test 01', N'0123456789', N'Dai Tay Duong', CAST(N'2021-03-02' AS Date), N'inactive', N'cust')
ALTER TABLE [dbo].[Feedback]  WITH CHECK ADD  CONSTRAINT [FK_Feedback_Order] FOREIGN KEY([orderID])
REFERENCES [dbo].[Order] ([orderID])
GO
ALTER TABLE [dbo].[Feedback] CHECK CONSTRAINT [FK_Feedback_Order]
GO
ALTER TABLE [dbo].[Order]  WITH CHECK ADD  CONSTRAINT [FK_Order_Discount] FOREIGN KEY([discountCode])
REFERENCES [dbo].[Discount] ([discountCode])
GO
ALTER TABLE [dbo].[Order] CHECK CONSTRAINT [FK_Order_Discount]
GO
ALTER TABLE [dbo].[Order]  WITH CHECK ADD  CONSTRAINT [FK_Order_User] FOREIGN KEY([email])
REFERENCES [dbo].[User] ([email])
GO
ALTER TABLE [dbo].[Order] CHECK CONSTRAINT [FK_Order_User]
GO
ALTER TABLE [dbo].[OrderDetail]  WITH CHECK ADD  CONSTRAINT [FK_OrderDetail_Car] FOREIGN KEY([carID])
REFERENCES [dbo].[Car] ([carID])
GO
ALTER TABLE [dbo].[OrderDetail] CHECK CONSTRAINT [FK_OrderDetail_Car]
GO
ALTER TABLE [dbo].[OrderDetail]  WITH CHECK ADD  CONSTRAINT [FK_OrderDetail_Order] FOREIGN KEY([orderID])
REFERENCES [dbo].[Order] ([orderID])
GO
ALTER TABLE [dbo].[OrderDetail] CHECK CONSTRAINT [FK_OrderDetail_Order]
GO
USE [master]
GO
ALTER DATABASE [CarRental] SET  READ_WRITE 
GO
