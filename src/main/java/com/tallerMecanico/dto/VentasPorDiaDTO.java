package com.tallerMecanico.dto;

public class VentasPorDiaDTO {
	private int day;
    private int month;
    private int year;
    private double total;

    public VentasPorDiaDTO(int day, int month, int year, double total) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.total = total;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
