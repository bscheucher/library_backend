package com.scheucher.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "loans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many-to-One relationship with Book
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    @NotNull(message = "Book is required")
    private Book book;

    // Many-to-One relationship with Member
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @NotNull(message = "Member is required")
    private Member member;

    @Column(name = "loan_date", nullable = false)
    private LocalDate loanDate;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status = LoanStatus.ACTIVE;

    @Column(name = "renewal_count")
    private Integer renewalCount = 0;

    @Column(name = "max_renewals")
    private Integer maxRenewals = 2;

    @Column(name = "fine_amount")
    private Double fineAmount = 0.0;

    @Column(name = "fine_paid")
    private Boolean finePaid = false;

    @Column(name = "notes")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issued_by_employee_id")
    private Employee issuedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "returned_to_employee_id")
    private Employee returnedTo;

    // Audit fields
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();
        if (loanDate == null) {
            loanDate = LocalDate.now();
        }
        if (dueDate == null) {
            dueDate = loanDate.plusDays(14); // Default loan period of 14 days
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }

    // Helper method to calculate if loan is overdue
    public boolean isOverdue() {
        return status == LoanStatus.ACTIVE &&
                LocalDate.now().isAfter(dueDate);
    }

    // Helper method to calculate days overdue
    public long getDaysOverdue() {
        if (!isOverdue()) {
            return 0;
        }
        return ChronoUnit.DAYS.between(dueDate, LocalDate.now());
    }

    // Helper method to calculate fine (assuming $0.50 per day)
    public double calculateFine() {
        if (!isOverdue()) {
            return 0.0;
        }
        return getDaysOverdue() * 0.50;
    }

    // Helper method to renew loan
    public boolean renewLoan(int days) {
        if (renewalCount >= maxRenewals || isOverdue()) {
            return false;
        }
        this.dueDate = this.dueDate.plusDays(days);
        this.renewalCount++;
        return true;
    }
}