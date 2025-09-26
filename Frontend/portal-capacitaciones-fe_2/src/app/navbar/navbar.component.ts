import { Component, Input, Output, EventEmitter } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule, MatToolbarModule, MatButtonModule],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  @Input() user: any;
  @Output() logout = new EventEmitter<void>();

  isAdmin(): boolean {
    if (!this.user) return false;
    return !!(this.user.admin || this.user.is_admin || this.user.isAdmin);
  }

  doLogout() {
    this.logout.emit();
  }
}
