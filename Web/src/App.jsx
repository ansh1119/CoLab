import React, { useState, useEffect } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { 
  Terminal, 
  Users, 
  Zap, 
  MessageSquare, 
  Code2, 
  ChevronRight, 
  Globe, 
  Cpu, 
  Layout, 
  ArrowUpRight 
} from 'lucide-react';

const Navbar = () => {
  const [scrolled, setScrolled] = useState(false);
  
  useEffect(() => {
    const handleScroll = () => setScrolled(window.scrollY > 50);
    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  return (
    <nav className={`fixed top-0 left-0 w-full z-50 transition-all duration-300 ${scrolled ? 'py-4' : 'py-6'}`}>
      <div className={`mx-auto max-w-7xl px-6 flex items-center justify-between rounded-full transition-all duration-500 ${scrolled ? 'glass mx-4 sm:mx-10 py-3 shadow-2xl' : ''}`}>
        <div className="flex items-center gap-2">
          <div className="w-10 h-10 rounded-xl bg-indigo-600 flex items-center justify-center shadow-lg shadow-indigo-500/30">
            <Terminal className="text-white w-6 h-6" />
          </div>
          <span className="text-2xl font-black gradient-text tracking-tight">CoLabCraft</span>
        </div>
        
        <div className="hidden md:flex items-center gap-8 text-sm font-medium text-slate-300">
          <a href="#" className="hover:text-indigo-400 transition-colors">Explore</a>
          <a href="#" className="hover:text-indigo-400 transition-colors">Hackathons</a>
          <a href="#" className="hover:text-indigo-400 transition-colors">Teams</a>
          <a href="#" className="hover:text-indigo-400 transition-colors">Docs</a>
        </div>
        
        <button className="px-6 py-2.5 rounded-full bg-indigo-600 text-white text-sm font-bold shadow-lg shadow-indigo-600/25 hover:bg-indigo-500 transition-all active:scale-95">
          Get Started
        </button>
      </div>
    </nav>
  );
};

const FeatureCard = ({ icon: Icon, title, desc, delay }) => (
  <motion.div 
    initial={{ opacity: 0, y: 20 }}
    whileInView={{ opacity: 1, y: 0 }}
    transition={{ duration: 0.5, delay }}
    viewport={{ once: true }}
    className="glass p-8 rounded-3xl glass-hover group"
  >
    <div className="w-14 h-14 rounded-2xl bg-indigo-500/10 flex items-center justify-center mb-6 group-hover:bg-indigo-500/20 transition-colors">
      <Icon className="text-indigo-400 w-8 h-8" />
    </div>
    <h3 className="text-xl font-bold mb-3">{title}</h3>
    <p className="text-slate-400 text-sm leading-relaxed">{desc}</p>
  </motion.div>
);

const HackathonCard = ({ title, date, tag, attendees, participants }) => (
  <motion.div 
    whileHover={{ y: -10 }}
    className="glass relative overflow-hidden rounded-[2.5rem] border-white/5"
  >
    <div className="absolute top-0 right-0 p-6">
      <div className="bg-indigo-500/20 text-indigo-400 px-4 py-1.5 rounded-full text-[10px] font-black uppercase tracking-widest border border-indigo-500/30">
        {tag}
      </div>
    </div>
    
    <div className="p-8 pt-16">
      <div className="text-indigo-400 text-xs font-bold mb-2 uppercase tracking-tight">{date}</div>
      <h3 className="text-2xl font-black mb-6 leading-tight">{title}</h3>
      
      <div className="flex items-center justify-between mt-auto pt-6 border-t border-white/5">
        <div className="flex -space-x-3">
          {[1,2,3,4].map(i => (
            <div key={i} className="w-10 h-10 rounded-full border-2 border-[#020617] bg-slate-800 flex items-center justify-center text-[10px] font-bold">
              {String.fromCharCode(64 + i)}
            </div>
          ))}
          <div className="w-10 h-10 rounded-full border-2 border-[#020617] bg-indigo-600 flex items-center justify-center text-[10px] font-bold">
            +{attendees}
          </div>
        </div>
        <button className="w-12 h-12 rounded-full glass flex items-center justify-center hover:bg-white/10 transition-colors">
          <ArrowUpRight className="w-5 h-5 text-indigo-400" />
        </button>
      </div>
    </div>
  </motion.div>
);

const App = () => {
  return (
    <div className="min-h-screen text-slate-100 selection:bg-indigo-500 selection:text-white">
      <div className="mesh-bg" />
      <Navbar />
      
      {/* Hero Section */}
      <section className="relative pt-48 pb-32 px-6 overflow-hidden">
        <div className="mx-auto max-w-7xl text-center">
          <motion.div
            initial={{ opacity: 0, scale: 0.9 }}
            animate={{ opacity: 1, scale: 1 }}
            transition={{ duration: 0.8 }}
            className="inline-flex items-center gap-2 px-4 py-2 rounded-full glass border-white/10 text-xs font-bold text-indigo-400 mb-8"
          >
            <span className="flex h-2 w-2 rounded-full bg-indigo-500 animate-pulse" />
            V2.0 ALPHA IS NOW LIVE
          </motion.div>
          
          <motion.h1 
            initial={{ opacity: 0, y: 30 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.8, delay: 0.2 }}
            className="text-6xl md:text-8xl font-black leading-[0.9] tracking-tighter mb-10"
          >
            Craft Your <br />
            <span className="gradient-text">Dream Team.</span>
          </motion.h1>
          
          <motion.p 
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.8, delay: 0.4 }}
            className="mx-auto max-w-2xl text-lg md:text-xl text-slate-400 mb-12"
          >
            The ultimate ecosystem for developers to find, collaborate, and win global hackathons together. Powered by smart matchmaking and instant recruitment.
          </motion.p>
          
          <motion.div 
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.8, delay: 0.6 }}
            className="flex flex-col sm:flex-row items-center justify-center gap-4"
          >
            <button className="w-full sm:w-auto px-10 py-5 rounded-[2rem] bg-indigo-600 text-lg font-black shadow-2xl shadow-indigo-600/40 hover:bg-indigo-500 hover:scale-105 transition-all text-white">
              Launch Dashboard
            </button>
            <button className="w-full sm:w-auto px-10 py-5 rounded-[2rem] glass text-lg font-black hover:bg-white/5 transition-all outline outline-1 outline-white/10">
              View Hackathons
            </button>
          </motion.div>
        </div>
        
        {/* Floating Elements */}
        <div className="absolute top-1/4 -left-20 w-80 h-80 bg-indigo-600/10 blur-[120px] rounded-full" />
        <div className="absolute bottom-1/4 -right-20 w-80 h-80 bg-purple-600/10 blur-[120px] rounded-full" />
      </section>

      {/* Stats Section */}
      <section className="py-20 px-6">
        <div className="mx-auto max-w-7xl">
          <div className="grid grid-cols-2 md:grid-cols-4 gap-8">
            {[
              { label: 'Active Crafters', val: '45K+' },
              { label: 'Projects Shipped', val: '12K+' },
              { label: 'Total Prize Pool', val: '$2.5M' },
              { label: 'Success Rate', val: '92%' }
            ].map((stat, i) => (
              <div key={i} className="text-center group">
                <div className="text-4xl md:text-5xl font-black mb-2 group-hover:scale-110 transition-transform duration-500">{stat.val}</div>
                <div className="text-slate-500 text-sm font-bold uppercase tracking-widest">{stat.label}</div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Hackathons Section */}
      <section className="py-32 px-6 bg-slate-900/40">
        <div className="mx-auto max-w-7xl">
          <div className="flex items-end justify-between mb-16">
            <div className="max-w-xl">
              <h2 className="text-4xl md:text-5xl font-black mb-6 leading-[1.1]">Trending <br />Global Hackathons</h2>
              <p className="text-slate-400">Handpicked high-stakes events waiting for your touch.</p>
            </div>
            <button className="hidden md:flex items-center gap-2 text-indigo-400 font-bold hover:gap-4 transition-all">
              See All Events <ChevronRight className="w-5 h-5" />
            </button>
          </div>
          
          <div className="grid md:grid-cols-3 gap-8">
            <HackathonCard title="Metaverse Builders" date="Oct 12-14" tag="Meta" attendees="120" />
            <HackathonCard title="AI Revolution 2026" date="Nov 01-05" tag="OpenAI" attendees="450" />
            <HackathonCard title="Web3 Titan Quest" date="Dec 20-30" tag="Solana" attendees="310" />
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="py-32 px-6">
        <div className="mx-auto max-w-7xl">
          <div className="text-center mb-20">
            <h2 className="text-4xl md:text-6xl font-black mb-6 italic tracking-tight uppercase">Platform Power</h2>
            <div className="w-24 h-1 bg-indigo-600 mx-auto rounded-full" />
          </div>
          
          <div className="grid md:grid-cols-3 gap-8">
            <FeatureCard 
              icon={Users} 
              title="Team Match" 
              desc="Our AI matches you with partners who complement your specific skill set and goals perfectly."
              delay={0.1}
            />
            <FeatureCard 
              icon={Cpu} 
              title="Vibrant Hub" 
              desc="Real-time chat, collaborative docs, and resource sharing all unified in one glassy interface."
              delay={0.2}
            />
            <FeatureCard 
              icon={Zap} 
              title="Instant Recruit" 
              desc="One click to post your requirements and get verified developer requests instantly."
              delay={0.3}
            />
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="py-32 px-6">
        <motion.div 
          whileInView={{ scale: [0.95, 1] }}
          transition={{ duration: 0.5 }}
          className="mx-auto max-w-7xl rounded-[3rem] overflow-hidden relative group"
        >
          <div className="absolute inset-0 bg-gradient-to-br from-indigo-600 to-purple-800" />
          <div className="absolute inset-0 bg-[url('https://grainy-gradients.vercel.app/noise.svg')] opacity-20 pointer-events-none" />
          
          <div className="relative p-12 md:p-32 text-center">
            <h2 className="text-4xl md:text-7xl font-black mb-8 leading-none">Ready to Build the <br />Next Big Thing?</h2>
            <p className="mx-auto max-w-xl text-indigo-100 text-lg mb-12 opacity-80">Join 45,000+ crafters and start your journey today. Your next team is just a click away.</p>
            <button className="px-12 py-6 rounded-full bg-white text-indigo-600 font-extrabold text-xl shadow-2xl hover:scale-105 transition-all">
              Join CoLabCraft Now
            </button>
          </div>
        </motion.div>
      </section>

      {/* Footer */}
      <footer className="py-12 px-6 border-t border-white/5">
        <div className="mx-auto max-w-7xl flex flex-col md:row items-center justify-between gap-8">
          <div className="flex items-center gap-2">
            <Terminal className="text-indigo-500 w-8 h-8" />
            <span className="text-xl font-black gradient-text">CoLabCraft</span>
          </div>
          <div className="text-slate-500 text-sm">© 2026 CoLabCraft. Crafted with passion for global builders.</div>
          <div className="flex gap-6">
            <a href="#" className="w-10 h-10 rounded-full glass flex items-center justify-center hover:bg-white/10 transition-colors">
              <Globe className="w-5 h-5 text-slate-400" />
            </a>
            <a href="#" className="w-10 h-10 rounded-full glass flex items-center justify-center hover:bg-white/10 transition-colors">
              <Code2 className="w-5 h-5 text-slate-400" />
            </a>
          </div>
        </div>
      </footer>
    </div>
  );
};

export default App;
