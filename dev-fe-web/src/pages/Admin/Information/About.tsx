import CMSLayout from "@/layouts/cms-layout";
import React from "react";
import { Mail, Github, Globe } from "lucide-react";
import demoAppImage from "../../../../public/logo.ico";
import { useTranslation } from "react-i18next";

const About: React.FC = () => {
  const {t} = useTranslation();
  return (
    <CMSLayout title={t("admin.menu.main.info.title")} subtitle={t("admin.menu.main.info.sub.about")}>
      <section className="max-w-4xl mx-auto px-4 py-8 text-gray-700 dark:text-gray-200 space-y-8">
        {/* Giới thiệu sản phẩm */}
        <div>
          <h2 className="text-3xl font-bold text-primary">
            Giới thiệu về RoomX
          </h2>
          <p className="mt-4">
            <strong>RoomX</strong> là hệ thống giúp đơn giản hóa việc{" "}
            <strong>quản lý và đặt phòng họp</strong> tại các tổ chức, công ty
            và trường học. Ứng dụng cung cấp giao diện trực quan, dễ sử dụng
            cùng nhiều tính năng như theo dõi lịch phòng, tạo sự kiện, phân
            quyền người dùng, v.v.
          </p>

          {/* Ảnh minh họa giao diện */}
          <img
            src={demoAppImage}
            alt="Teamwork illustration"
            className="w-1/3 md:w-1/4 mx-auto mt-6"
          />

          <div className="mt-6 bg-muted/30 p-4 rounded-xl border dark:border-muted">
            <p>
              <strong>Phiên bản hiện tại:</strong> 1.0.0 (Beta)
            </p>
            <p>
              <strong>Phát hành:</strong> Tháng 5 năm 2025
            </p>
            <p>
              <strong>Bản quyền:</strong> Mã nguồn mở
            </p>
          </div>
        </div>

        {/* Nhóm phát triển */}
        <div>
          <h3 className="text-2xl font-semibold text-green-600">
            Về nhóm phát triển
          </h3>
          <p className="mt-2">
            RoomX được phát triển bởi <strong>"nhóm 25" sinh viên</strong> thuộc
            ngành Kỹ thuật Phần mềm – Khoa Công nghệ Thông tin, Đại học Công
            nghiệp TP.HCM (IUH).
          </p>
          <p className="mt-2">
            Dự án được thực hiện với mục tiêu học tập, rèn luyện kỹ năng thực tế
            và góp phần xây dựng các công cụ hữu ích cho cộng đồng lập trình
            viên.
          </p>

          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 mt-6">
            {[
              {
                name: "Nguyễn Văn Giàu",
                role: "Backend Developer",
                email: "giau32002@gmail.com",
                avatar: "https://avatars.githubusercontent.com/u/76744254?v=4",
                github: "https://github.com/jiag293",
              },
              {
                name: "Huỳnh Văn Sang",
                role: "Frontend Developer",
                email: "huynhvansang.020102@gmail.com",
                avatar: "https://avatars.githubusercontent.com/u/133298437?v=4",
                github: "https://github.com/SangHynh",
              },
            ].map((member, idx) => (
              <div
                key={idx}
                className="flex items-center gap-4 p-4 bg-muted/20 rounded-lg border dark:border-muted shadow-sm hover:shadow-md transition-shadow duration-300"
              >
                <img
                  src={member.avatar}
                  alt={member.name}
                  className="w-16 h-16 rounded-full object-cover"
                />
                <div>
                  <h4 className="font-semibold text-lg">{member.name}</h4>
                  <p className="text-sm text-muted-foreground">{member.role}</p>
                  <div className="flex items-center gap-2 text-sm text-muted-foreground mt-1">
                    <Mail size={14} />
                    <span>{member.email}</span>
                  </div>
                  <div className="flex items-center gap-2 text-sm mt-1 text-blue-500">
                    <Github size={14} />
                    <a
                      href={member.github}
                      target="_blank"
                      rel="noopener noreferrer"
                      className="hover:underline"
                    >
                      {member.github.replace("https://", "")}
                    </a>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Mục tiêu */}
        <div>
          <h3 className="text-2xl font-semibold text-green-600">
            Mục tiêu của chúng tôi
          </h3>
          <ul className="list-disc pl-6 mt-2 space-y-1">
            <li>Thiết kế hệ thống quản lý đặt phòng tiện lợi và hiệu quả.</li>
            <li>Áp dụng kiến thức đã học vào dự án thực tiễn.</li>
            <li>
              Rèn luyện kỹ năng teamwork, Git, giao tiếp và triển khai hệ thống.
            </li>
            <li>
              Thúc đẩy tư duy giải quyết vấn đề và sáng tạo sản phẩm thực tế.
            </li>
            <li>
              Góp phần phát triển các dự án mã nguồn mở phục vụ cộng đồng.
            </li>
          </ul>
        </div>

        {/* Quote truyền cảm hứng */}
        <blockquote className="italic border-l-4 border-green-500 pl-4 text-muted-foreground mt-6">
          “Xây dựng sản phẩm thực tế là cách tốt nhất để học lập trình.” – Nhóm
          RoomX
        </blockquote>

        {/* Liên hệ */}
        <div>
          <h3 className="text-2xl font-semibold text-green-600">
            Liên hệ và đóng góp
          </h3>
          <p className="mt-2">
            RoomX luôn chào đón các ý tưởng đóng góp, phản hồi hoặc hợp tác phát
            triển. Bạn có thể liên hệ chúng tôi qua các kênh sau:
          </p>
          <ul className="mt-4 space-y-2">
            <li className="flex items-center gap-2">
              <Mail size={16} /> <span>giau32002@gmail.com</span>
            </li>
            <li className="flex items-center gap-2">
              <Mail size={16} /> <span>huynhvansang.020102@gmail.com</span>
            </li>
            <li className="flex items-center gap-2">
              <Github size={16} />{" "}
              <a
                href="https://github.com/jiag293/roomx"
                target="_blank"
                className="hover:underline"
              >
                github.com/jiag293/roomx
              </a>
            </li>
            <li className="flex items-center gap-2">
              <Globe size={16} /> <span>www.roomx.dev</span>
            </li>
          </ul>
        </div>

        {/* Hình teamwork minh họa */}
        <img
          src="https://user-images.githubusercontent.com/49567393/132992023-9715d770-4225-497a-9eea-041e3d037186.gif"
          alt="Teamwork illustration"
          className="w-2/3 md:w-1/2 mx-auto mt-8"
        />

        <p className="text-center text-muted-foreground pt-8">
          © 2025 RoomX Project. Được phát triển với 💚 bởi sinh viên IUH.
        </p>
      </section>
    </CMSLayout>
  );
};

export default About;
