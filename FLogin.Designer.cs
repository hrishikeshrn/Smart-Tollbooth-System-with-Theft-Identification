namespace TollBooth
{
    partial class FLogin
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
			this.cmbtoll = new System.Windows.Forms.ComboBox();
			this.txtuser = new System.Windows.Forms.TextBox();
			this.txtpass = new System.Windows.Forms.TextBox();
			this.btnlogin = new System.Windows.Forms.Button();
			this.btnexit = new System.Windows.Forms.Button();
			this.panel1 = new System.Windows.Forms.Panel();
			this.panel1.SuspendLayout();
			this.SuspendLayout();
			// 
			// cmbtoll
			// 
			this.cmbtoll.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
			this.cmbtoll.Font = new System.Drawing.Font("Segoe UI", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.cmbtoll.FormattingEnabled = true;
			this.cmbtoll.Location = new System.Drawing.Point(24, 21);
			this.cmbtoll.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
			this.cmbtoll.Name = "cmbtoll";
			this.cmbtoll.Size = new System.Drawing.Size(275, 29);
			this.cmbtoll.TabIndex = 0;
			this.cmbtoll.SelectedIndexChanged += new System.EventHandler(this.cmbtoll_SelectedIndexChanged);
			// 
			// txtuser
			// 
			this.txtuser.Font = new System.Drawing.Font("Segoe UI", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.txtuser.Location = new System.Drawing.Point(24, 81);
			this.txtuser.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
			this.txtuser.Name = "txtuser";
			this.txtuser.Size = new System.Drawing.Size(275, 29);
			this.txtuser.TabIndex = 1;
			this.txtuser.Text = "MS";
			// 
			// txtpass
			// 
			this.txtpass.Font = new System.Drawing.Font("Segoe UI", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.txtpass.Location = new System.Drawing.Point(24, 146);
			this.txtpass.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
			this.txtpass.Name = "txtpass";
			this.txtpass.PasswordChar = '*';
			this.txtpass.Size = new System.Drawing.Size(275, 29);
			this.txtpass.TabIndex = 2;
			this.txtpass.Text = "123";
			// 
			// btnlogin
			// 
			this.btnlogin.Font = new System.Drawing.Font("Segoe UI Semibold", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.btnlogin.Location = new System.Drawing.Point(24, 206);
			this.btnlogin.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
			this.btnlogin.Name = "btnlogin";
			this.btnlogin.Size = new System.Drawing.Size(111, 36);
			this.btnlogin.TabIndex = 3;
			this.btnlogin.Text = "Login";
			this.btnlogin.UseVisualStyleBackColor = true;
			this.btnlogin.Click += new System.EventHandler(this.btnlogin_Click);
			// 
			// btnexit
			// 
			this.btnexit.Font = new System.Drawing.Font("Segoe UI Semibold", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.btnexit.Location = new System.Drawing.Point(164, 206);
			this.btnexit.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
			this.btnexit.Name = "btnexit";
			this.btnexit.Size = new System.Drawing.Size(111, 36);
			this.btnexit.TabIndex = 4;
			this.btnexit.Text = "Exit";
			this.btnexit.UseVisualStyleBackColor = true;
			this.btnexit.Click += new System.EventHandler(this.btnexit_Click);
			// 
			// panel1
			// 
			this.panel1.BackColor = System.Drawing.Color.White;
			this.panel1.Controls.Add(this.cmbtoll);
			this.panel1.Controls.Add(this.btnexit);
			this.panel1.Controls.Add(this.txtuser);
			this.panel1.Controls.Add(this.btnlogin);
			this.panel1.Controls.Add(this.txtpass);
			this.panel1.Location = new System.Drawing.Point(516, 30);
			this.panel1.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
			this.panel1.Name = "panel1";
			this.panel1.Size = new System.Drawing.Size(352, 326);
			this.panel1.TabIndex = 5;
			// 
			// FLogin
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.BackgroundImage = global::TollBooth.Properties.Resources.login;
			this.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
			this.ClientSize = new System.Drawing.Size(871, 405);
			this.Controls.Add(this.panel1);
			this.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
			this.Name = "FLogin";
			this.Text = "Login";
			this.Load += new System.EventHandler(this.FLogin_Load);
			this.panel1.ResumeLayout(false);
			this.panel1.PerformLayout();
			this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.ComboBox cmbtoll;
        private System.Windows.Forms.TextBox txtuser;
        private System.Windows.Forms.TextBox txtpass;
        private System.Windows.Forms.Button btnlogin;
        private System.Windows.Forms.Button btnexit;
        private System.Windows.Forms.Panel panel1;
    }
}