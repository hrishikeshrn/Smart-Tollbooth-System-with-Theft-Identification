namespace TollBooth
{
    partial class Form1
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
			this.components = new System.ComponentModel.Container();
			this.panel1 = new System.Windows.Forms.Panel();
			this.lbluserid = new System.Windows.Forms.Label();
			this.buttonStop = new System.Windows.Forms.Button();
			this.buttonStart = new System.Windows.Forms.Button();
			this.label3 = new System.Windows.Forms.Label();
			this.label2 = new System.Windows.Forms.Label();
			this.label1 = new System.Windows.Forms.Label();
			this.progressBar1 = new System.Windows.Forms.ProgressBar();
			this.comboBox2 = new System.Windows.Forms.ComboBox();
			this.comboBox1 = new System.Windows.Forms.ComboBox();
			this.serialPort1 = new System.IO.Ports.SerialPort(this.components);
			this.panel2 = new System.Windows.Forms.Panel();
			this.button2 = new System.Windows.Forms.Button();
			this.button1 = new System.Windows.Forms.Button();
			this.btnsearch = new System.Windows.Forms.Button();
			this.lblcollection = new System.Windows.Forms.Label();
			this.lblmsg = new System.Windows.Forms.Label();
			this.lblvehicaltype = new System.Windows.Forms.Label();
			this.label10 = new System.Windows.Forms.Label();
			this.lblvehicalno = new System.Windows.Forms.Label();
			this.label8 = new System.Windows.Forms.Label();
			this.lblmobile = new System.Windows.Forms.Label();
			this.label6 = new System.Windows.Forms.Label();
			this.lblusernname = new System.Windows.Forms.Label();
			this.label4 = new System.Windows.Forms.Label();
			this.panel1.SuspendLayout();
			this.panel2.SuspendLayout();
			this.SuspendLayout();
			// 
			// panel1
			// 
			this.panel1.Controls.Add(this.lbluserid);
			this.panel1.Controls.Add(this.buttonStop);
			this.panel1.Controls.Add(this.buttonStart);
			this.panel1.Controls.Add(this.label3);
			this.panel1.Controls.Add(this.label2);
			this.panel1.Controls.Add(this.label1);
			this.panel1.Controls.Add(this.progressBar1);
			this.panel1.Controls.Add(this.comboBox2);
			this.panel1.Controls.Add(this.comboBox1);
			this.panel1.Dock = System.Windows.Forms.DockStyle.Top;
			this.panel1.Location = new System.Drawing.Point(0, 0);
			this.panel1.Margin = new System.Windows.Forms.Padding(4);
			this.panel1.Name = "panel1";
			this.panel1.Size = new System.Drawing.Size(956, 95);
			this.panel1.TabIndex = 0;
			// 
			// lbluserid
			// 
			this.lbluserid.AutoSize = true;
			this.lbluserid.Font = new System.Drawing.Font("Segoe UI", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.lbluserid.Location = new System.Drawing.Point(776, 10);
			this.lbluserid.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
			this.lbluserid.Name = "lbluserid";
			this.lbluserid.Size = new System.Drawing.Size(20, 23);
			this.lbluserid.TabIndex = 8;
			this.lbluserid.Text = "0";
			this.lbluserid.Visible = false;
			// 
			// buttonStop
			// 
			this.buttonStop.Enabled = false;
			this.buttonStop.Location = new System.Drawing.Point(840, 38);
			this.buttonStop.Margin = new System.Windows.Forms.Padding(4);
			this.buttonStop.Name = "buttonStop";
			this.buttonStop.Size = new System.Drawing.Size(100, 28);
			this.buttonStop.TabIndex = 34;
			this.buttonStop.Text = "Stop";
			this.buttonStop.UseVisualStyleBackColor = true;
			this.buttonStop.Click += new System.EventHandler(this.buttonStop_Click);
			// 
			// buttonStart
			// 
			this.buttonStart.Location = new System.Drawing.Point(723, 38);
			this.buttonStart.Margin = new System.Windows.Forms.Padding(4);
			this.buttonStart.Name = "buttonStart";
			this.buttonStart.Size = new System.Drawing.Size(100, 28);
			this.buttonStart.TabIndex = 33;
			this.buttonStart.Text = "Start";
			this.buttonStart.UseVisualStyleBackColor = true;
			this.buttonStart.Click += new System.EventHandler(this.buttonStart_Click);
			// 
			// label3
			// 
			this.label3.AutoSize = true;
			this.label3.Location = new System.Drawing.Point(487, 14);
			this.label3.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
			this.label3.Name = "label3";
			this.label3.Size = new System.Drawing.Size(48, 17);
			this.label3.TabIndex = 40;
			this.label3.Text = "Status";
			// 
			// label2
			// 
			this.label2.AutoSize = true;
			this.label2.Location = new System.Drawing.Point(255, 14);
			this.label2.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
			this.label2.Name = "label2";
			this.label2.Size = new System.Drawing.Size(75, 17);
			this.label2.TabIndex = 39;
			this.label2.Text = "Baud Rate";
			// 
			// label1
			// 
			this.label1.AutoSize = true;
			this.label1.Location = new System.Drawing.Point(17, 14);
			this.label1.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
			this.label1.Name = "label1";
			this.label1.Size = new System.Drawing.Size(82, 17);
			this.label1.TabIndex = 38;
			this.label1.Text = "Port Names";
			// 
			// progressBar1
			// 
			this.progressBar1.Location = new System.Drawing.Point(487, 38);
			this.progressBar1.Margin = new System.Windows.Forms.Padding(4);
			this.progressBar1.Name = "progressBar1";
			this.progressBar1.Size = new System.Drawing.Size(212, 26);
			this.progressBar1.TabIndex = 37;
			// 
			// comboBox2
			// 
			this.comboBox2.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
			this.comboBox2.FormattingEnabled = true;
			this.comboBox2.Items.AddRange(new object[] {
            "9600",
            "115200"});
			this.comboBox2.Location = new System.Drawing.Point(255, 41);
			this.comboBox2.Margin = new System.Windows.Forms.Padding(4);
			this.comboBox2.Name = "comboBox2";
			this.comboBox2.Size = new System.Drawing.Size(211, 24);
			this.comboBox2.TabIndex = 36;
			// 
			// comboBox1
			// 
			this.comboBox1.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
			this.comboBox1.FormattingEnabled = true;
			this.comboBox1.Location = new System.Drawing.Point(17, 43);
			this.comboBox1.Margin = new System.Windows.Forms.Padding(4);
			this.comboBox1.Name = "comboBox1";
			this.comboBox1.Size = new System.Drawing.Size(211, 24);
			this.comboBox1.TabIndex = 35;
			this.comboBox1.SelectedIndexChanged += new System.EventHandler(this.comboBox1_SelectedIndexChanged);
			// 
			// serialPort1
			// 
			this.serialPort1.DataReceived += new System.IO.Ports.SerialDataReceivedEventHandler(this.serialPort1_DataReceived);
			// 
			// panel2
			// 
			this.panel2.Controls.Add(this.button2);
			this.panel2.Controls.Add(this.button1);
			this.panel2.Controls.Add(this.btnsearch);
			this.panel2.Controls.Add(this.lblcollection);
			this.panel2.Controls.Add(this.lblmsg);
			this.panel2.Controls.Add(this.lblvehicaltype);
			this.panel2.Controls.Add(this.label10);
			this.panel2.Controls.Add(this.lblvehicalno);
			this.panel2.Controls.Add(this.label8);
			this.panel2.Controls.Add(this.lblmobile);
			this.panel2.Controls.Add(this.label6);
			this.panel2.Controls.Add(this.lblusernname);
			this.panel2.Controls.Add(this.label4);
			this.panel2.Dock = System.Windows.Forms.DockStyle.Fill;
			this.panel2.Location = new System.Drawing.Point(0, 95);
			this.panel2.Margin = new System.Windows.Forms.Padding(4);
			this.panel2.Name = "panel2";
			this.panel2.Size = new System.Drawing.Size(956, 257);
			this.panel2.TabIndex = 1;
			this.panel2.Paint += new System.Windows.Forms.PaintEventHandler(this.panel2_Paint);
			// 
			// button2
			// 
			this.button2.Location = new System.Drawing.Point(624, 191);
			this.button2.Name = "button2";
			this.button2.Size = new System.Drawing.Size(85, 41);
			this.button2.TabIndex = 11;
			this.button2.Text = "Logout";
			this.button2.UseVisualStyleBackColor = true;
			this.button2.Click += new System.EventHandler(this.button2_Click);
			// 
			// button1
			// 
			this.button1.Font = new System.Drawing.Font("Segoe UI", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.button1.Location = new System.Drawing.Point(796, 213);
			this.button1.Margin = new System.Windows.Forms.Padding(4);
			this.button1.Name = "button1";
			this.button1.Size = new System.Drawing.Size(144, 30);
			this.button1.TabIndex = 10;
			this.button1.Text = "View Report";
			this.button1.UseVisualStyleBackColor = true;
			this.button1.Click += new System.EventHandler(this.button1_Click);
			// 
			// btnsearch
			// 
			this.btnsearch.Font = new System.Drawing.Font("Segoe UI Semibold", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.btnsearch.Location = new System.Drawing.Point(879, 4);
			this.btnsearch.Margin = new System.Windows.Forms.Padding(4);
			this.btnsearch.Name = "btnsearch";
			this.btnsearch.Size = new System.Drawing.Size(73, 30);
			this.btnsearch.TabIndex = 5;
			this.btnsearch.Text = "Refresh";
			this.btnsearch.UseVisualStyleBackColor = true;
			this.btnsearch.Click += new System.EventHandler(this.btnsearch_Click);
			// 
			// lblcollection
			// 
			this.lblcollection.AutoSize = true;
			this.lblcollection.Font = new System.Drawing.Font("Segoe UI", 13F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.lblcollection.Location = new System.Drawing.Point(484, 4);
			this.lblcollection.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
			this.lblcollection.Name = "lblcollection";
			this.lblcollection.Size = new System.Drawing.Size(127, 30);
			this.lblcollection.TabIndex = 9;
			this.lblcollection.Text = "User Name";
			// 
			// lblmsg
			// 
			this.lblmsg.AutoSize = true;
			this.lblmsg.Font = new System.Drawing.Font("Segoe UI", 23F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.lblmsg.ForeColor = System.Drawing.Color.Red;
			this.lblmsg.Location = new System.Drawing.Point(313, 162);
			this.lblmsg.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
			this.lblmsg.Name = "lblmsg";
			this.lblmsg.Size = new System.Drawing.Size(100, 52);
			this.lblmsg.TabIndex = 8;
			this.lblmsg.Text = "msg";
			// 
			// lblvehicaltype
			// 
			this.lblvehicaltype.AutoSize = true;
			this.lblvehicaltype.Font = new System.Drawing.Font("Segoe UI", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.lblvehicaltype.Location = new System.Drawing.Point(188, 181);
			this.lblvehicaltype.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
			this.lblvehicaltype.Name = "lblvehicaltype";
			this.lblvehicaltype.Size = new System.Drawing.Size(46, 23);
			this.lblvehicaltype.TabIndex = 7;
			this.lblvehicaltype.Text = "type";
			// 
			// label10
			// 
			this.label10.AutoSize = true;
			this.label10.Font = new System.Drawing.Font("Segoe UI", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.label10.Location = new System.Drawing.Point(36, 181);
			this.label10.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
			this.label10.Name = "label10";
			this.label10.Size = new System.Drawing.Size(109, 23);
			this.label10.TabIndex = 6;
			this.label10.Text = "Vehical Type";
			// 
			// lblvehicalno
			// 
			this.lblvehicalno.AutoSize = true;
			this.lblvehicalno.Font = new System.Drawing.Font("Segoe UI", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.lblvehicalno.Location = new System.Drawing.Point(188, 124);
			this.lblvehicalno.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
			this.lblvehicalno.Name = "lblvehicalno";
			this.lblvehicalno.Size = new System.Drawing.Size(33, 23);
			this.lblvehicalno.TabIndex = 5;
			this.lblvehicalno.Text = "No";
			// 
			// label8
			// 
			this.label8.AutoSize = true;
			this.label8.Font = new System.Drawing.Font("Segoe UI", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.label8.Location = new System.Drawing.Point(36, 124);
			this.label8.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
			this.label8.Name = "label8";
			this.label8.Size = new System.Drawing.Size(99, 23);
			this.label8.TabIndex = 4;
			this.label8.Text = "Vehical No.";
			// 
			// lblmobile
			// 
			this.lblmobile.AutoSize = true;
			this.lblmobile.Font = new System.Drawing.Font("Segoe UI", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.lblmobile.Location = new System.Drawing.Point(188, 75);
			this.lblmobile.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
			this.lblmobile.Name = "lblmobile";
			this.lblmobile.Size = new System.Drawing.Size(66, 23);
			this.lblmobile.TabIndex = 3;
			this.lblmobile.Text = "mobile";
			this.lblmobile.Click += new System.EventHandler(this.lblmobile_Click);
			// 
			// label6
			// 
			this.label6.AutoSize = true;
			this.label6.Font = new System.Drawing.Font("Segoe UI", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.label6.Location = new System.Drawing.Point(36, 75);
			this.label6.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
			this.label6.Name = "label6";
			this.label6.Size = new System.Drawing.Size(99, 23);
			this.label6.TabIndex = 2;
			this.label6.Text = "Mobile No.";
			// 
			// lblusernname
			// 
			this.lblusernname.AutoSize = true;
			this.lblusernname.Font = new System.Drawing.Font("Segoe UI", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.lblusernname.Location = new System.Drawing.Point(188, 26);
			this.lblusernname.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
			this.lblusernname.Name = "lblusernname";
			this.lblusernname.Size = new System.Drawing.Size(54, 23);
			this.lblusernname.TabIndex = 1;
			this.lblusernname.Text = "name";
			this.lblusernname.Click += new System.EventHandler(this.lblusernname_Click);
			// 
			// label4
			// 
			this.label4.AutoSize = true;
			this.label4.Font = new System.Drawing.Font("Segoe UI", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.label4.Location = new System.Drawing.Point(36, 26);
			this.label4.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
			this.label4.Name = "label4";
			this.label4.Size = new System.Drawing.Size(97, 23);
			this.label4.TabIndex = 0;
			this.label4.Text = "User Name";
			// 
			// Form1
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.ClientSize = new System.Drawing.Size(956, 352);
			this.Controls.Add(this.panel2);
			this.Controls.Add(this.panel1);
			this.Margin = new System.Windows.Forms.Padding(4);
			this.Name = "Form1";
			this.Text = "TollBooth";
			this.Load += new System.EventHandler(this.Form1_Load);
			this.panel1.ResumeLayout(false);
			this.panel1.PerformLayout();
			this.panel2.ResumeLayout(false);
			this.panel2.PerformLayout();
			this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Button buttonStop;
        private System.Windows.Forms.Button buttonStart;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.ProgressBar progressBar1;
        private System.Windows.Forms.ComboBox comboBox2;
        private System.Windows.Forms.ComboBox comboBox1;
        private System.IO.Ports.SerialPort serialPort1;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.Label lblvehicaltype;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.Label lblvehicalno;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.Label lblmobile;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label lblusernname;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label lbluserid;
        private System.Windows.Forms.Label lblmsg;
        private System.Windows.Forms.Label lblcollection;
        private System.Windows.Forms.Button btnsearch;
        private System.Windows.Forms.Button button1;
		private System.Windows.Forms.Button button2;
	}
}

