using System;
using System.Data;
using System.Windows.Forms;
using System.IO.Ports;
using System.Threading;

namespace TollBooth
{
    public partial class Form1 : Form
    {
        string RxString;
        DataTable dts;
        //public static string ConnString = @"Data Source=103.209.144.155,1533;Initial Catalog=tollboothDB;User ID=toll;Password=ms@123";
        public Form1()
        {
            InitializeComponent();
            getAvailablePorts();
        }
        void getAvailablePorts()
        {
            String[] ports = SerialPort.GetPortNames();
            comboBox1.Items.AddRange(ports);
        }
        private void Form1_Load(object sender, EventArgs e)
        {
            try
            {
                this.Text = "TollBooth : - " + GeneralFunctions.tollname;
                lblusernname.Text = "";
                lblmobile.Text = "";
                lblvehicalno.Text = "";
                lblvehicaltype.Text = "";
                lblmsg.Text = "";
                string error = "", qry = "";
                qry = "SELECT sum(PaymentAmt) as PaymentAmt FROM dbo.tollpayments where TollId='" + GeneralFunctions.tollid + "' and PayDate='" + DateTime.Now.ToString("yyyy/MM/dd") + "'";
                object amt = GeneralFunctions.ExecuteScalar(qry, out error);

                lblcollection.Text = "Today's Collection : " + amt;
                //string error = "", qry = "";
                //qry = "select * from mlogin";
                //DataTable dt = GeneralFunctions.ExecuteDataTable(qry, out error);
                //dataGridView1.DataSource = dt;
            }
            catch (Exception ex)
            {

            }   
        }

        private void buttonStart_Click(object sender, EventArgs e)
        {
            serialPort1.PortName = comboBox1.Text;
            serialPort1.BaudRate = Convert.ToInt32(comboBox2.Text);
            serialPort1.Open();
            progressBar1.Value = 100;
            if (serialPort1.IsOpen)
            {
                buttonStart.Enabled = false;
                //btndisplay.Enabled = true;
                buttonStop.Enabled = true;
            }
        }

        private void buttonStop_Click(object sender, EventArgs e)
        {
            if (serialPort1.IsOpen)
            {
                serialPort1.Close();
                progressBar1.Value = 0;
                buttonStart.Enabled = true;
                buttonStop.Enabled = false;
                // btndisplay.Enabled = false;
            }
        }
        int userid = 0;
        private void DisplayText(object sender, EventArgs e)
        {
            //serialPort1.Write("B");
            string s = RxString.Trim();
            string error = "", qry = "";
            qry = @"select * from mlogin where RFIDcode='" + s +"'";
            DataTable dtlog = GeneralFunctions.ExecuteDataTable(qry, out error);
            if (dtlog.Rows.Count > 0)
            {
                userid = Convert.ToInt32(dtlog.Rows[0]["regId"].ToString());
                lbluserid.Text = userid.ToString();
                lblusernname.Text = dtlog.Rows[0]["regName"].ToString();
                lblmobile.Text = dtlog.Rows[0]["mobileNo"].ToString();
                lblvehicalno.Text = dtlog.Rows[0]["vehicleNo"].ToString();
                lblvehicaltype.Text = dtlog.Rows[0]["vtype"].ToString();
            }
            else
            {
                lbluserid.Text = "";
                lblusernname.Text = "";
                lblmobile.Text = "";
                lblvehicalno.Text = "";
                lblvehicaltype.Text = "";

            }
            if (userid > 0)
            {
                qry = @"select Count(*) from complainregister where compStatus='N' AND regId=" + userid;
                int stolenstatus =Convert.ToInt32(GeneralFunctions.ExecuteScalar(qry, out error));
                if (stolenstatus  == 1)
                {
                    lblmsg.Text = "Vehicle Stolen";
                    lblmsg.ForeColor = System.Drawing.Color.Red;
                    serialPort1.Write("B");
                }
                else
                {
                    qry = @"select count(*) from tollpayments where Status='N' AND PayDate='" + DateTime.Now.ToString("yyyy/MM/dd") + "' AND regId=" + userid;
                    int paidstatus = Convert.ToInt32(GeneralFunctions.ExecuteScalar(qry, out error));
                    if (paidstatus == 1)
                    {
                        lblmsg.Text = "Toll Paid";
                        lblmsg.ForeColor = System.Drawing.Color.Green;
                        serialPort1.Write("A");

                        qry = " Update tollpayments SET Status='Y' where PayDate='" + DateTime.Now.ToString("yyyy/MM/dd") + "' AND regId=" + userid;
                        bool status = GeneralFunctions.ExecuteNonQuery(qry, out error);

                        if (status == true)
                        { }
                    }
                    else
                    {
                        lblmsg.Text = "Toll UnPaid";
                        lblmsg.ForeColor = System.Drawing.Color.Red;
                        serialPort1.Write("C");
                    }
                }
            }
            else
            {
                MessageBox.Show("User Does Not Exist");
            }
        }

        private void serialPort1_DataReceived(object sender, SerialDataReceivedEventArgs e)
        {
            Thread.Sleep(100);
            while (serialPort1.BytesToRead > 0)
            {
                RxString = serialPort1.ReadExisting();
                this.Invoke(new EventHandler(DisplayText));
            }
        }

        private void btnsearch_Click(object sender, EventArgs e)
        {
            string error = "", qry = "";
            qry = "SELECT sum(PaymentAmt) as PaymentAmt FROM dbo.tollpayments where TollId='" + GeneralFunctions.tollid + "' and PayDate='" + DateTime.Now.ToString("yyyy/MM/dd") + "'";
            object amt = GeneralFunctions.ExecuteScalar(qry, out error);
            lblcollection.Text = "Today's Collection : " + amt;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            PymentReport pr = new PymentReport();
            pr.ShowDialog();
        }

		private void panel2_Paint(object sender, PaintEventArgs e)
		{

		}

		private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
		{

		}

		private void lblmobile_Click(object sender, EventArgs e)
		{

		}

		private void lblusernname_Click(object sender, EventArgs e)
		{

		}

		private void button2_Click(object sender, EventArgs e)
		{
			this.Close();
			FLogin fl = new FLogin();
			fl.ShowDialog();
		}
	}
}
